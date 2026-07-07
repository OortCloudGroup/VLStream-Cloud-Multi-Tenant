package org.springblade.test.detection;

import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.output.Joints;
import ai.djl.util.JsonUtils;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.UUID;
import cn.smartjavaai.common.cv.SmartImageFactory;
import cn.smartjavaai.common.entity.R;
import cn.smartjavaai.common.enums.DeviceEnum;
import cn.smartjavaai.common.enums.VideoSourceType;
import cn.smartjavaai.common.utils.ImageUtils;
import cn.smartjavaai.pose.config.PoseModelConfig;
import cn.smartjavaai.pose.enums.PoseModelEnum;
import cn.smartjavaai.pose.model.PoseDetModelFactory;
import cn.smartjavaai.pose.model.PoseModel;
import cn.smartjavaai.pose.stream.PoseStreamDetectionListener;
import cn.smartjavaai.pose.stream.PoseStreamDetector;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;

/**
 * 姿态估计demo
 * 模型下载地址：https://pan.baidu.com/s/1pPYyl1V2CpcMYCO8CJQHGg?pwd=1234 提取码: 1234
 * 文档地址：http://doc.smartjavaai.cn/
 * @author dwj
 */
@Slf4j
public class PoseDetDemo {

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
     * 获取姿态估计模型
     * 注意事项：
     * 1、更多模型请查看文档：http://doc.smartjavaai.cn
     */
    public PoseModel getModel(){
        PoseModelConfig config = new PoseModelConfig();
        //姿态估计模型，切换模型需要同时修改modelEnum及modelPath
        config.setModelEnum(PoseModelEnum.YOLO11N_POSE_ONNX);
        config.setModelPath("D:\\BaiduNetdiskDownload\\SmartJavaAI\\姿态估计模型\\yolo11n-pose-onnx");
        config.setDevice(device);
        //置信度阈值
        config.setThreshold(0.25f);
        return PoseDetModelFactory.getInstance().getModel(config);
    }



    /**
     * 姿态估计
     */
    @Test
    public void poseDet(){
        try {
            PoseModel detectorModel = getModel();
            //创建Image对象，可以从文件、url、InputStream创建、BufferedImage、Base64创建，具体使用方法可以查看文档
            Image image = SmartImageFactory.getInstance().fromFile(Paths.get("src/main/resources/pose/pose_soccer.png"));
            R<Joints[]> result = detectorModel.detect(image);
            if(result.isSuccess()){
                log.info("姿态估计结果：{}", JSONObject.toJSONString(result.getData()));
            }else{
                log.info("姿态估计失败：{}", result.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 姿态估计并绘制检测结果
     */
    @Test
    public void poseDetAndDraw(){
        try {
            PoseModel detectorModel = getModel();
            R<Joints[]> result = detectorModel.detectAndDraw("src/main/resources/pose/pose_soccer.png","output/pose_detected.png");
            if(result.isSuccess()){
                log.info("姿态估计结果：{}", JSONObject.toJSONString(result.getData()));
            }else{
                log.info("姿态估计失败：{}", result.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 姿态估计并绘制检测结果
     */
    @Test
    public void poseDetAndDraw2(){
        try {
            PoseModel detectorModel = getModel();
            //创建Image对象，可以从文件、url、InputStream创建、BufferedImage、Base64创建，具体使用方法可以查看文档
            Image image = SmartImageFactory.getInstance().fromFile(Paths.get("src/main/resources/pose/pose_soccer.png"));
            //可以根据后续业务场景使用detectedImage
            Image drawImage = detectorModel.detectAndDraw(image);
            //保存图片
            ImageUtils.save(drawImage, "pose_detected2.png", "output");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 视频流姿态估计
     */
    @Test
    public void testStream(){
        PoseStreamDetector detector = new PoseStreamDetector.Builder()
                //视频源类型：支持视频流、本地摄像头、视频文件
                .sourceType(VideoSourceType.STREAM)
                //视频流地址，支持rtsp、rtmp、http等常见视频流
                .streamUrl("rtsp://admin:oort301301@192.168.88.129:554/h264/ch1/main/av_stream")
                //每隔多少帧检测一次（需要根据模型检测速度决定）
                .frameDetectionInterval(20)
                //目标检测模型
                .detectorModel(getModel())
                //回调函数：检测到指定目标时触发（getModel中可指定模型检测的物体）
                .listener(new PoseStreamDetectionListener() {
                    @Override
                    public void onPoseDetected(Joints[] joints, Image image) {
                        log.info("pose frame detected at {}", LocalDateTimeUtil.now().toString());
                        log.info("pose result: {}", JsonUtils.toJson(joints));
                        if (image != null) {
                            if (joints != null) {
                                for (Joints joint : joints) {
                                    image.drawJoints(joint);
                                }
                            }
                            ImageUtils.save(image, "test" + UUID.fastUUID().toString() + ".png", "D:\\images\\");
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
     * 视频文件姿态估计
     */
    @Test
    public void testVideoFile(){
        PoseStreamDetector detector = new PoseStreamDetector.Builder()
                //视频源类型：支持视频流、本地摄像头、视频文件
                .sourceType(VideoSourceType.STREAM)
                //摄像头序号
                .streamUrl("rtsp://admin:oort301301@192.168.88.127/Streaming/Channels/101?transportmode=unicast&profile=Profile_1")
                //每隔多少帧检测一次（需要根据模型检测速度决定）
                .frameDetectionInterval(5)
                //目标检测模型
                .detectorModel(getModel())
                //同物体重复检测时间间隔，单位s
//                .repeatGap(5)
                //回调函数：检测到指定目标时触发（getModel中可指定模型检测的物体）
                .listener(new PoseStreamDetectionListener() {
                    @Override
                    public void onPoseDetected(Joints[] joints, Image image) {
                        log.info("pose frame detected at {}", LocalDateTimeUtil.now().toString());
                        log.info("pose result: {}", JsonUtils.toJson(joints));
                        if (image != null) {
                            if (joints != null) {
                                for (Joints joint : joints) {
                                    image.drawJoints(joint);
                                }
                            }
                            ImageUtils.save(image, "test" + UUID.fastUUID().toString() + ".png", "D:\\images\\");
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
