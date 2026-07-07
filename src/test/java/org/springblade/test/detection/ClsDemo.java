package org.springblade.test.detection;

import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.util.JsonUtils;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.UUID;
import cn.smartjavaai.cls.config.ClsModelConfig;
import cn.smartjavaai.cls.enums.ClsModelEnum;
import cn.smartjavaai.cls.model.ClsModel;
import cn.smartjavaai.cls.model.ClsModelFactory;
import cn.smartjavaai.cls.stream.ClsStreamDetectionListener;
import cn.smartjavaai.cls.stream.ClsStreamDetector;
import cn.smartjavaai.common.cv.SmartImageFactory;
import cn.smartjavaai.common.entity.DetectionInfo;
import cn.smartjavaai.common.entity.R;
import cn.smartjavaai.common.enums.DeviceEnum;
import cn.smartjavaai.common.enums.VideoSourceType;
import cn.smartjavaai.common.utils.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 图像分类模型demo
 * @author dwj
 */
@Slf4j
public class ClsDemo {


    //设备类型
    public static DeviceEnum device = DeviceEnum.CPU;

    @BeforeClass
    public static void beforeAll() throws IOException {
        //将图片处理的底层引擎切换为 OpenCV
        SmartImageFactory.setEngine(SmartImageFactory.Engine.OPENCV);
        //修改缓存路径
//        Config.setCachePath("/Users/xxx/smartjavaai_cache");
    }


    public ClsModel getModel(){
        ClsModelConfig config = new ClsModelConfig();
        //切换模型需要同时修改modelEnum及modelPath
        config.setModelEnum(ClsModelEnum.YOLOV8);
        //模型所在路径，synset.txt也需要放在同目录下
        config.setModelPath("D:\\BaiduNetdiskDownload\\SmartJavaAI\\图像分类（CLS）\\yolov8\\yolov8m-cls.onnx");
        // 指定允许的类别
//        config.setAllowedClasses(Arrays.asList("dog","car"));
        //指定返回检测数量
        config.setDevice(device);
        //置信度阈值
        config.setThreshold(0.5f);
        return ClsModelFactory.getInstance().getModel(config);
    }

    /**
     * 实例分割
     */
    @Test
    public void detect(){
        try {
            ClsModel detectorModel = getModel();
            //创建Image对象，可以从文件、url、InputStream创建、BufferedImage、Base64创建，具体使用方法可以查看文档
            Image image = SmartImageFactory.getInstance().fromFile(Paths.get("src/main/resources/dog_bike_car.jpg"));
            R<Classifications> result = detectorModel.detect(image);
            if(result.isSuccess()){
                if(CollectionUtils.isNotEmpty(result.getData().getClassNames())){
                    //分数最高分类
                    log.info("分类识别结果：{}", result.getData().best().toString());
                    //按分数排序前5个结果
//                log.info("动作识别结果：{}", result.getData().topK(5).toString());
                }else{
                    log.info("未识别到分类");
                }
            }else{
                log.info("分类识别失败：{}", result.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 视频流分类
     */
    @Test
    public void testStream(){
        ClsStreamDetector detector = new ClsStreamDetector.Builder()
                //视频源类型：支持视频流、本地摄像头、视频文件
                .sourceType(VideoSourceType.STREAM)
                //视频流地址，支持rtsp、rtmp、http等常见视频流
                .streamUrl("rtsp://admin:oort301301@192.168.88.129:554/h264/ch1/main/av_stream")
                //每隔多少帧检测一次（需要根据模型检测速度决定）
                .frameDetectionInterval(20)
                //分类模型
                .detectorModel(getModel())
                //回调函数
                .listener(new ClsStreamDetectionListener() {
                    @Override
                    public void onObjectDetected(List<DetectionInfo> detectionInfoList, Image image) {
                        log.info("时间：" + LocalDateTimeUtil.now().toString());
                        log.info("检测结果：{}", JsonUtils.toJson(detectionInfoList));
                        ImageUtils.save(image, "test" + UUID.fastUUID().toString() + ".png", "D:\\images\\");
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
        CountDownLatch latch = new CountDownLatch(1);
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
