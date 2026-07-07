package org.springblade.test.detection;

import ai.djl.modality.cv.Image;
import ai.djl.util.JsonUtils;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.UUID;
import cn.smartjavaai.common.cv.SmartImageFactory;
import cn.smartjavaai.common.entity.DetectionInfo;
import cn.smartjavaai.common.entity.DetectionResponse;
import cn.smartjavaai.common.entity.R;
import cn.smartjavaai.common.enums.DeviceEnum;
import cn.smartjavaai.common.enums.VideoSourceType;
import cn.smartjavaai.common.utils.ImageUtils;
import cn.smartjavaai.objectdetection.config.PersonDetModelConfig;
import cn.smartjavaai.objectdetection.enums.PersonDetectorModelEnum;
import cn.smartjavaai.objectdetection.model.person.PersonDetModel;
import cn.smartjavaai.objectdetection.model.person.PersonDetModelFactory;
import cn.smartjavaai.objectdetection.stream.StreamDetectionListener;
import cn.smartjavaai.objectdetection.stream.StreamDetector;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 行人检测案例
 * 模型下载地址：https://pan.baidu.com/s/1EWfExw7pYjKEH5uR5wf3Rw?pwd=1234 提取码: 1234
 * 文档地址：http://doc.smartjavaai.cn/
 * @author dwj
 */
@Slf4j
public class PersonDetectDemo {

    //设备类型
    public static DeviceEnum device = DeviceEnum.CPU;

    @BeforeClass
    public static void beforeAll() throws IOException {
        //将图片处理的底层引擎切换为 OpenCV
        SmartImageFactory.setEngine(SmartImageFactory.Engine.OPENCV);
        //修改缓存路径
//        Config.setCachePath("/Users/xxx/smartjavaai_cache");
    }

    /**
     * 获取行人检测模型
     */
    public PersonDetModel getModel(){
        PersonDetModelConfig config = new PersonDetModelConfig();
        //行人检测模型，切换模型需要同时修改modelEnum及modelPath
        config.setModelEnum(PersonDetectorModelEnum.YOLOV8_PERSON);
        //模型所在路径
        config.setModelPath("D:\\BaiduNetdiskDownload\\SmartJavaAI\\行人检测模型\\yolov8-person\\yolov8n-person.onnx");
        //指定返回检测数量
        config.setTopK(100);
        config.setDevice(device);
        //置信度阈值
        config.setThreshold(0.5f);
        return PersonDetModelFactory.getInstance().getModel(config);
    }



    /**
     * 行人检测
     */
    @Test
    public void objectDetection(){
        try {
            PersonDetModel detectorModel = getModel();
            //创建Image对象，可以从文件、url、InputStream创建、BufferedImage、Base64创建，具体使用方法可以查看文档
            Image image = SmartImageFactory.getInstance().fromFile(Paths.get("src/main/resources/person/person.png"));
            R<DetectionResponse> result = detectorModel.detect(image);
            if(result.isSuccess()){
                log.info("行人检测结果：{}", JSONObject.toJSONString(result.getData()));
            }else{
                log.info("行人检测失败：{}", result.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 行人检测并绘制检测结果
     */
    @Test
    public void objectDetectionAndDraw(){
        try {
            PersonDetModel detectorModel = getModel();
            //保存绘制后图片以及返回检测结果
            R<DetectionResponse> result = detectorModel.detectAndDraw("src/main/resources/person/person.png","output/person_detected.png");
            if(result.isSuccess()){
                log.info("行人检测结果：{}", JSONObject.toJSONString(result.getData()));
            }else{
                log.info("行人检测失败：{}", result.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 行人检测并绘制检测结果
     */
    @Test
    public void objectDetectionAndDraw2(){
        try {
            PersonDetModel detectorModel = getModel();
            //创建Image对象，可以从文件、url、InputStream创建、BufferedImage、Base64创建，具体使用方法可以查看文档
            Image image = SmartImageFactory.getInstance().fromFile(Paths.get("src/main/resources/person/person.png"));
            //可以根据后续业务场景使用detectedImage
            R<DetectionResponse> result = detectorModel.detectAndDraw(image);
            if(result.isSuccess()){
                log.info("行人检测结果：{}", JSONObject.toJSONString(result.getData()));
                //保存图片
                ImageUtils.save(result.getData().getDrawnImage(), "person_result.png", "output");
            }else{
                log.info("行人检测失败：{}", result.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 视频文件目标检测
     */
    @Test
    public void testVideoFile(){
        StreamDetector detector = new StreamDetector.Builder()
                //视频源类型：支持视频流、本地摄像头、视频文件
                .sourceType(VideoSourceType.STREAM)
                //摄像头序号
                .streamUrl("rtsp://admin:oort301301@192.168.88.129:554/h264/ch1/main/av_stream")
                //每隔多少帧检测一次（需要根据模型检测速度决定）
                .frameDetectionInterval(5)
                //目标检测模型
                .detectorModel(getModel())
                //同物体重复检测时间间隔，单位s
//                .repeatGap(5)
                //回调函数：检测到指定目标时触发（getModel中可指定模型检测的物体）
                .listener(new StreamDetectionListener() {
                    @Override
                    public void onObjectDetected(List<DetectionInfo> detectionInfoList, Image image) {
                        log.info("时间：" + LocalDateTimeUtil.now().toString());
                        log.info("检测结果：{}", JsonUtils.toJson(detectionInfoList));
                        //绘制检测结果
                        ImageUtils.drawRectAndText(image, detectionInfoList);
                        //保存图片
                        ImageUtils.save(image, "test"+ UUID.fastUUID().toString() +".png","D:\\images\\");
                        if (image != null){
                            ImageUtils.releaseOpenCVMat(image);
                        }
                    }

                    @Override
                    public void onStreamEnded() {
                        log.info("视频流检测结束");
                    }

                    @Override
                    public void onStreamDisconnected() {
                        log.info("视频流断开连接");
                    }
                }).build();
        detector.startDetection();
        //阻塞主线程
        CountDownLatch latch = new CountDownLatch(1);
        try {
            latch.await(); // 一直阻塞，直到被 countDown
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
