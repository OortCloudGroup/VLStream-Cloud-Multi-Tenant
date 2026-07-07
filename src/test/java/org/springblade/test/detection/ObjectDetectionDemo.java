package org.springblade.test.detection;

import ai.djl.modality.cv.Image;
import ai.djl.util.JsonUtils;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.UUID;
import cn.smartjavaai.common.cv.SmartImageFactory;
import cn.smartjavaai.common.entity.DetectionInfo;
import cn.smartjavaai.common.entity.DetectionResponse;
import cn.smartjavaai.common.enums.DeviceEnum;
import cn.smartjavaai.common.enums.VideoSourceType;
import cn.smartjavaai.common.utils.ImageUtils;
import cn.smartjavaai.objectdetection.config.DetectorModelConfig;
import cn.smartjavaai.objectdetection.enums.DetectorModelEnum;
import cn.smartjavaai.objectdetection.model.DetectorModel;
import cn.smartjavaai.objectdetection.model.ObjectDetectionModelFactory;
import cn.smartjavaai.objectdetection.stream.StreamDetectionListener;
import cn.smartjavaai.objectdetection.stream.StreamDetector;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 目标检测模型demo
 * 模型下载地址：https://pan.baidu.com/s/10aTOLBlR6EG-sq6g0OkAWg?pwd=1234 提取码: 1234
 * 文档地址：http://doc.smartjavaai.cn/
 * @author dwj
 */
@Slf4j
public class ObjectDetectionDemo {


    //设备类型
    public static DeviceEnum device = DeviceEnum.CPU;

    @BeforeClass
    public static void beforeAll() throws IOException {
        //将图片处理的底层引擎切换为 OpenCV
        SmartImageFactory.setEngine(SmartImageFactory.Engine.BUFFEREDIMAGE);
        //修改缓存路径
//        Config.setCachePath("/Users/xxx/smartjavaai_cache");
    }

    /**
     * 获取目标检测模型
     * 注意事项：
     * 1、更多模型请查看文档：http://doc.smartjavaai.cn/objectdetect.html
     */
    public DetectorModel getModel(){
        DetectorModelConfig config = new DetectorModelConfig();
        //目标检测模型，切换模型需要同时修改modelEnum及modelPath
        config.setModelEnum(DetectorModelEnum.YOLOV8_OFFICIAL_ONNX);
        //模型所在路径，synset.txt也需要放在同目录下
        config.setModelPath("D:\\BaiduNetdiskDownload\\SmartJavaAI\\目标检测\\yolov8模型\\yolov8m.onnx");
        // 指定允许的类别
//            config.setAllowedClasses(Arrays.asList("person","car"));
        //指定返回检测数量
        config.setTopK(100);
        config.setDevice(device);
        //置信度阈值
        config.setThreshold(0.5f);
        return ObjectDetectionModelFactory.getInstance().getModel(config);
    }



    /**
     * 目标检测
     */
    @Test
    public void objectDetection(){
        try {
            DetectorModel detectorModel = getModel();
            //创建Image对象，可以从文件、url、InputStream创建、BufferedImage、Base64创建，具体使用方法可以查看文档
            Image image = SmartImageFactory.getInstance().fromFile("src/main/resources/object_detection.jpg");
            DetectionResponse detectionResponse = detectorModel.detect(image);
            log.info("目标检测结果：{}", JSONObject.toJSONString(detectionResponse));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 目标检测并绘制检测结果
     */
    @Test
    public void objectDetectionAndDraw(){
        try {
            DetectorModel detectorModel = getModel();
            detectorModel.detectAndDraw("src/main/resources/object_detection.jpg","output/object_detection_detected.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 目标检测并绘制检测结果,返回BufferedImage
     */
    @Test
    public void objectDetectionAndDraw2(){
        try {
            DetectorModel detectorModel = getModel();
            String imagePath = "src/main/resources/object_detection.jpg";
            //创建Image对象，可以从文件、url、InputStream创建、BufferedImage、Base64创建，具体使用方法可以查看文档
            Image image = SmartImageFactory.getInstance().fromFile(imagePath);
            //可以根据后续业务场景使用detectedImage
            DetectionResponse detectionResponse = detectorModel.detectAndDraw(image);
            log.info("目标检测结果：{}", JSONObject.toJSONString(detectionResponse));
            if(detectionResponse != null && detectionResponse.getDrawnImage() != null){
                ImageUtils.save(detectionResponse.getDrawnImage(), "output/object_detection_detected2.png");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 使用自己训练的模型检测
     */
    @Test
    public void objectDetectionWithCustomModel(){
        try {
            ObjectDetectionModelFactory.removeFromCache(DetectorModelEnum.YOLOV8_CUSTOM_ONNX);
            DetectorModelConfig config = new DetectorModelConfig();
            //目标检测模型，切换模型需要同时修改modelEnum及modelPath
            config.setModelEnum(DetectorModelEnum.YOLOV8_CUSTOM_ONNX);
            //模型所在路径，synset.txt也需要放在同目录下(分类文件，具体请看文档：http://doc.smartjavaai.cn/objectdetect.html#%E4%BD%BF%E7%94%A8%E8%87%AA%E5%B7%B1%E8%AE%AD%E7%BB%83%E7%9A%84%E6%A8%A1%E5%9E%8B%E6%A3%80%E6%B5%8B)
            config.setModelPath("D:\\360极速浏览器X下载\\目标检测.onnx");
            //模型训练时图片宽度
            config.putCustomParam("width", 640);//resize 宽
            //模型训练时图片高度
            config.putCustomParam("height", 640);// resize 高
            config.putCustomParam("nmsThreshold", 0.5f);
//            config.setThreshold(0.15f);
            // 指定允许的类别
//            config.setAllowedClasses(Arrays.asList("person"));
            //指定返回检测数量
//            config.setTopK(100);
            config.setDevice(device);
            DetectorModel detectorModel = ObjectDetectionModelFactory.getInstance().getModel(config);
            detectorModel.detectAndDraw("src/main/resources/205.png","src/main/resources/test_detected.png");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("模型检测错误：{}", e.getMessage());
        }
    }

    /**
     * tensorflow2目标检测
     * 注意事项：
     * 1、百度网盘只提供部分模型，更多tensorflow模型可以前往官网下载：https://github.com/tensorflow/models/blob/master/research/object_detection/g3doc/tf2_detection_zoo.md
     */
    @Test
    public void objectDetection3(){
        try {
            DetectorModelConfig config = new DetectorModelConfig();
            //指定模型枚举，可以通过modelPath指定不同tensorflow模型
            config.setModelEnum(DetectorModelEnum.TENSORFLOW2_OFFICIAL);
            //模型路径，需解压模型压缩包，可以通过modelPath指定不同tensorflow模型
            config.setModelPath("/Users/wenjie/Documents/develop/model/tensorflow/ssd_mobilenet_v2_320x320_coco17_tpu-8");
//            config.putCustomParam("synsetUrl", "https://raw.githubusercontent.com/tensorflow/models/master/research/object_detection/data/mscoco_label_map.pbtxt");
//            config.putCustomParam("synsetPath", "/Users/wenjie/Downloads/mscoco_label_map.pbtxt.txt");
            //分类文件，需下载放入模型路径下
            config.putCustomParam("synsetFileName", "mscoco.pbtxt");
            // 指定允许的类别
//            config.setAllowedClasses(Arrays.asList("person"));
            //指定返回检测数量
            config.setTopK(100);
            config.setDevice(device);
            DetectorModel detectorModel = ObjectDetectionModelFactory.getInstance().getModel(config);
            //创建Image对象，可以从文件、url、InputStream创建、BufferedImage、Base64创建，具体使用方法可以查看文档
            Image image = SmartImageFactory.getInstance().fromFile("src/main/resources/dog_bike_car.jpg");
            DetectionResponse detectionResponse = detectorModel.detect(image);
            log.info("目标检测结果：{}", JSONObject.toJSONString(detectionResponse));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 视频流目标检测
     */
    @Test
    public void testStream(){
        StreamDetector detector = new StreamDetector.Builder()
                //视频源类型：支持视频流、本地摄像头、视频文件
                .sourceType(VideoSourceType.STREAM)
                //视频流地址，支持rtsp、rtmp、http等常见视频流
                .streamUrl("rtsp://admin:oort301301@192.168.88.129:554/h264/ch1/main/av_stream")
                //每隔多少帧检测一次（需要根据模型检测速度决定）
                .frameDetectionInterval(20)
                //目标检测模型
                .detectorModel(getModel())
                //回调函数：检测到指定目标时触发（getModel中可指定模型检测的物体）
                .listener(new StreamDetectionListener() {

                    /**
                     * 建议把耗时操作放到新线程里执行
                     * @param detectionInfoList 目标信息列表
                     * @param image 检测到的图片
                     */
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


    /**
     * 本地摄像头目标检测
     */
    @Test
    public void testLocalCamera(){
        StreamDetector detector = new StreamDetector.Builder()
                //视频源类型：支持视频流、本地摄像头、视频文件
                .sourceType(VideoSourceType.CAMERA)
                //摄像头序号
                .cameraIndex(0)
                //每隔多少帧检测一次（需要根据模型检测速度决定）
                .frameDetectionInterval(5)
                //目标检测模型
                .detectorModel(getModel())
                //回调函数：检测到指定目标时触发（getModel中可指定模型检测的物体）
                .listener(new StreamDetectionListener() {
                    @Override
                    public void onObjectDetected(List<DetectionInfo> detectionInfoList, Image image) {
                        log.info("时间：" + LocalDateTimeUtil.now().toString());
                        log.info("检测结果：{}", JsonUtils.toJson(detectionInfoList));
                        //绘制检测结果
                        ImageUtils.drawRectAndText(image, detectionInfoList);
                        //保存图片
                        ImageUtils.save(image, "test"+ UUID.fastUUID().toString() +".png","/Users/wenjie/Downloads");
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

    /**
     * 视频文件目标检测
     */
    @Test
    public void testVideoFile(){
        StreamDetector detector = new StreamDetector.Builder()
                //视频源类型：支持视频流、本地摄像头、视频文件
                .sourceType(VideoSourceType.FILE)
                //摄像头序号
                .streamUrl("girl.mp4")
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
                        ImageUtils.save(image, "test"+ UUID.fastUUID().toString() +".png","/Users/wenjie/Downloads");
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
