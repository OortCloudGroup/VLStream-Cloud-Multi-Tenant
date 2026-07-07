package org.springblade.test.detection;

import ai.djl.modality.cv.Image;
import ai.djl.util.JsonUtils;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.UUID;
import cn.smartjavaai.common.cv.SmartImageFactory;
import cn.smartjavaai.common.entity.DetectionInfo;
import cn.smartjavaai.common.entity.DetectionResponse;
import cn.smartjavaai.common.entity.R;
import cn.smartjavaai.common.enums.VideoSourceType;
import cn.smartjavaai.common.utils.ImageUtils;
import cn.smartjavaai.face.config.FaceDetConfig;
import cn.smartjavaai.face.constant.FaceDetectConstant;
import cn.smartjavaai.face.enums.FaceDetModelEnum;
import cn.smartjavaai.face.factory.FaceDetModelFactory;
import cn.smartjavaai.face.model.facedect.FaceDetModel;
import cn.smartjavaai.face.utils.FaceUtils;
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
 * 人脸检测模型demo
 * 模型下载地址：https://pan.baidu.com/s/1d2YlJ2YOdGn3Y-AegyAhmQ?pwd=1234 提取码: 1234
 * 文档地址：http://doc.smartjavaai.cn/
 *
 * @author dwj
 */
@Slf4j
public class FaceDetDemo {


    public static String imgPath = "src/main/resources/iu_1.jpg";

    @BeforeClass
    public static void beforeAll() throws IOException {
        //将图片处理的底层引擎切换为 OpenCV
        SmartImageFactory.setEngine(SmartImageFactory.Engine.OPENCV);
        //修改缓存路径
//        Config.setCachePath("/Users/xxx/smartjavaai_cache");
    }


    /**
     * 获取人脸检测模型（均衡模型）
     * 均衡模型：兼顾速度和精度
     * 注意事项：SmartJavaAI提供了多种模型选择(更多模型，请查看文档)，切换模型需要同时修改modelEnum及modelPath
     *
     * @return
     */
    public FaceDetModel getFaceDetModel() {
        FaceDetConfig config = new FaceDetConfig();
        //人脸检测模型，SmartJavaAI提供了多种模型选择(更多模型，请查看文档)，切换模型需要同时修改modelEnum及modelPath
        config.setModelEnum(FaceDetModelEnum.MTCNN);
        //下载模型并替换本地路径，下载地址：https://pan.baidu.com/s/10l22x5fRz_gwLr8EAHa1Jg?pwd=1234 提取码: 1234
        config.setModelPath("/Users/wenjie/Documents/develop/model/face_model/mtcnn");
        //只返回相似度大于该值的人脸,需要根据实际情况调整，分值越大越严格容易漏检，分值越小越宽松容易误识别
        config.setConfidenceThreshold(0.5f);
        //用于去除重复的人脸框，当两个框的重叠度超过该值时，只保留一个
        config.setNmsThresh(FaceDetectConstant.NMS_THRESHOLD);
        return FaceDetModelFactory.getInstance().getModel(config);
    }


    /**
     * 获取人脸检测模型（高精度模型）
     * 注意事项：高精度模型，识别准确度高，速度慢
     *
     * @return
     */
    public FaceDetModel getProFaceDetModel() {
        FaceDetConfig config = new FaceDetConfig();
        //人脸检测模型，SmartJavaAI提供了多种模型选择(更多模型，请查看文档)，切换模型需要同时修改modelEnum及modelPath
        config.setModelEnum(FaceDetModelEnum.RETINA_FACE);
        //下载模型并替换本地路径，下载地址：https://pan.baidu.com/s/10l22x5fRz_gwLr8EAHa1Jg?pwd=1234 提取码: 1234
        config.setModelPath("D:\\RetinaFace\\retinaface.pt");
        //只返回相似度大于该值的人脸,需要根据实际情况调整，分值越大越严格容易漏检，分值越小越宽松容易误识别
        config.setConfidenceThreshold(0.5f);
        //用于去除重复的人脸框，当两个框的重叠度超过该值时，只保留一个
        config.setNmsThresh(FaceDetectConstant.NMS_THRESHOLD);
        return FaceDetModelFactory.getInstance().getModel(config);
    }

    /**
     * 获取人脸检测模型（极速模型）
     * 注意事项：极速模型，识别准确度低，速度快
     *
     * @return
     */
    public FaceDetModel getFastFaceDetModel() {
        FaceDetConfig config = new FaceDetConfig();
        //人脸检测模型，SmartJavaAI提供了多种模型选择(更多模型，请查看文档)，切换模型需要同时修改modelEnum及modelPath
        config.setModelEnum(FaceDetModelEnum.YOLOV5_FACE_320);
        //下载模型并替换本地路径，下载地址：https://pan.baidu.com/s/10l22x5fRz_gwLr8EAHa1Jg?pwd=1234 提取码: 1234
        config.setModelPath("D:\\BaiduNetdiskDownload\\SmartJavaAI\\人脸模型\\人脸检测模型\\yolov5-face\\yolov5face-n-0.5-320x320.onnx");
        //只返回相似度大于该值的人脸,需要根据实际情况调整，分值越大越严格容易漏检，分值越小越宽松容易误识别
        config.setConfidenceThreshold(0.5f);
        //用于去除重复的人脸框，当两个框的重叠度超过该值时，只保留一个
        config.setNmsThresh(FaceDetectConstant.NMS_THRESHOLD);
        return FaceDetModelFactory.getInstance().getModel(config);
    }

    /**
     * 获取Seetaface6 人脸检测模型
     * 注意：不支持macos
     *
     * @return
     */
    public FaceDetModel getSeetaface6DetModel() {
        FaceDetConfig config = new FaceDetConfig();
        //指定模型
        config.setModelEnum(FaceDetModelEnum.SEETA_FACE6_MODEL);
        //指定模型路径：请根据实际情况替换为本地模型文件的绝对路径（下载地址：https://pan.baidu.com/s/10l22x5fRz_gwLr8EAHa1Jg?pwd=1234 提取码: 1234）
        config.setModelPath("C:/Users/Administrator/Downloads/sf3.0_models/sf3.0_models");
        config.setConfidenceThreshold(0.9);
        return FaceDetModelFactory.getInstance().getModel(config);
    }


    /**
     * 人脸检测
     * 注意事项：
     * 1、此用例使用均衡模型，可以切换高精度模型或极速模型
     */
    @Test
    public void testFaceDetect() {
        try {
            FaceDetModel faceModel = getFaceDetModel();
            //创建Image对象，可以从文件、url、InputStream创建、BufferedImage、Base64创建，具体使用方法可以查看文档
            Image image = SmartImageFactory.getInstance().fromFile(imgPath);
            R<DetectionResponse> detectedResult = faceModel.detect(image);
            if (detectedResult.isSuccess()) {
                log.info("人脸检测结果：{}", JSONObject.toJSONString(detectedResult.getData()));
                //裁剪人脸保存
                for (DetectionInfo detectionInfo : detectedResult.getData().getDetectionInfoList()) {
                    Image faceImage = FaceUtils.cropFace(image, detectionInfo.getDetectionRectangle());
                    ImageUtils.save(faceImage, "output/face_" + detectionInfo.getDetectionRectangle().getX() + "_" + detectionInfo.getDetectionRectangle().getY() + ".jpg");
                }
            } else {
                log.info("人脸检测失败：{}", detectedResult.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 人脸检测并绘制人脸框
     */
    @Test
    public void testFaceDetectAndDraw() {
        try {
            FaceDetModel faceModel = getFaceDetModel();
            R<DetectionResponse> detectedResult = faceModel.detectAndDraw("src/main/resources/largest_selfie.jpg", "output/largest_selfie_detected.png");
            if (detectedResult.isSuccess()) {
                log.info("人脸检测成功：{}", JsonUtils.toJson(detectedResult.getData()));
            } else {
                log.info("人脸检测失败：{}", detectedResult.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 人脸检测并绘制人脸框,返回BufferedImage
     *
     */
    @Test
    public void testFaceDetectAndDraw2() {
        try {
            FaceDetModel faceModel = getFaceDetModel();
            //创建Image对象，可以从文件、url、InputStream创建、BufferedImage、Base64创建，具体使用方法可以查看文档
            Image image = SmartImageFactory.getInstance().fromFile(imgPath);
            R<DetectionResponse> detectionResponseR = faceModel.detectAndDraw(image);
            if (detectionResponseR.isSuccess()) {
                log.info("人脸检测成功：{}", JsonUtils.toJson(detectionResponseR.getData()));
                ImageUtils.save(detectionResponseR.getData().getDrawnImage(), "output/iu_1_detect.png");
            } else {
                log.info("人脸检测失败：{}", detectionResponseR.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


    /**
     * 人脸检测(Seetaface6)
     * 注意事项：不支持macos
     */
    @Test
    public void testFaceDetectSeetaface6() {
        try {
            FaceDetModel faceModel = getSeetaface6DetModel();
            //创建Image对象，可以从文件、url、InputStream创建、BufferedImage、Base64创建，具体使用方法可以查看文档
            Image image = SmartImageFactory.getInstance().fromFile(imgPath);
            R<DetectionResponse> detectedResult = faceModel.detect(image);
            if (detectedResult.isSuccess()) {
                log.info("人脸检测结果：{}", JSONObject.toJSONString(detectedResult.getData()));
            } else {
                log.info("人脸检测失败：{}", detectedResult.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 视频流人脸检测
     */
    @Test
    public void testVideoStreamFaceDetect() {
        FaceDetModel faceModel = getProFaceDetModel();
        StreamDetector detector = new StreamDetector.Builder()
                //视频源类型：支持视频流、本地摄像头、视频文件
                .sourceType(VideoSourceType.STREAM)
                //视频流地址
                .streamUrl("rtsp://admin:oort301301@192.168.88.127/Streaming/Channels/101?transportmode=unicast&profile=Profile_1")
                //每隔多少帧检测一次（需要根据模型检测速度决定）
                .frameDetectionInterval(5)
                //目标检测模型
                .detectorModel(faceModel::getPool)
                //回调函数：检测到指定目标时触发（getFastFaceDetModel 中可指定模型检测的物体）
                .listener(new StreamDetectionListener() {
                    @Override
                    public void onObjectDetected(List<DetectionInfo> detectionInfoList, Image image) {
                        log.info("时间：" + LocalDateTimeUtil.now().toString());
                        log.info("检测结果：{}", JsonUtils.toJson(detectionInfoList));
                        //绘制检测结果
                        ImageUtils.drawRectAndText(image, detectionInfoList);
                        //保存图片
                        ImageUtils.save(image, "face_" + UUID.fastUUID().toString() + ".png", "D:\\images\\");
                        if (image != null) {
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
