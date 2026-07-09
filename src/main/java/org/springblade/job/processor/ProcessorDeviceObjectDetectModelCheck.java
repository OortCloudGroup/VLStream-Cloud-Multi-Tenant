package org.springblade.job.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tech.powerjob.worker.core.processor.ProcessResult;
import tech.powerjob.worker.core.processor.TaskContext;
import tech.powerjob.worker.core.processor.sdk.BasicProcessor;

/**
 * Scene management camera detection tasks(Compatible class name): Reuse ProcessorDeviceObjectDetect realization. 
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessorDeviceObjectDetectModelCheck implements BasicProcessor {

    private final ProcessorDeviceObjectDetect delegate;

    @Override
    public ProcessResult process(TaskContext taskContext) {
        return delegate.process(taskContext);
    }
}
