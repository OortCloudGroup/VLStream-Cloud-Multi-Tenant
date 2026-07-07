package org.springblade.vlstream.protocol.gb28181.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/control")
@Tag(name = "协议-国标控制API", description = "国标控制API")
public class GbApiControlController {

	@Operation(summary = "云台控制")
	@GetMapping("/ptz")
	public void ptz(@RequestParam String serial,
					  @RequestParam String command,
					  @RequestParam(required = false) Integer channel,
					  @RequestParam(required = false) String code,
					  @RequestParam(required = false) Integer speed) {
		// 兼容接口占位，暂不执行国标信令
	}

	@Operation(summary = "预置位控制")
	@GetMapping("/preset")
	public void preset(@RequestParam String serial,
						 @RequestParam String command,
						 @RequestParam(required = false) Integer channel,
						 @RequestParam(required = false) String code,
						 @RequestParam(required = false) String name,
						 @RequestParam(required = false) Integer preset) {
		// 兼容接口占位，暂不执行国标信令
	}
}
