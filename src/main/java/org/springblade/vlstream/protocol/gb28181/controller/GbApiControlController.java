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
@Tag(name = "protocol-National standard controlAPI", description = "National standard controlAPI")
public class GbApiControlController {

	@Operation(summary = "PTZ control")
	@GetMapping("/ptz")
	public void ptz(@RequestParam String serial,
					  @RequestParam String command,
					  @RequestParam(required = false) Integer channel,
					  @RequestParam(required = false) String code,
					  @RequestParam(required = false) Integer speed) {
		// Compatible interface placeholder, National standard signaling will not be implemented for the time being.
	}

	@Operation(summary = "Preset position control")
	@GetMapping("/preset")
	public void preset(@RequestParam String serial,
						 @RequestParam String command,
						 @RequestParam(required = false) Integer channel,
						 @RequestParam(required = false) String code,
						 @RequestParam(required = false) String name,
						 @RequestParam(required = false) Integer preset) {
		// Compatible interface placeholder, National standard signaling will not be implemented for the time being.
	}
}
