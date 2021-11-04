package com.jaspreet.foodsharing;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/data")
public class RetroController {

	@Autowired
	RetroRepository repo;
	
	@GetMapping(value = "/getAll/{username}")
	public ResponseEntity<List<ElementModel>> getAll(@PathVariable(required = true, value = "username") String username){
		List<ElementModel> result = repo.getOtherUserData(username);
		return new ResponseEntity<List<ElementModel>>(result,HttpStatus.OK);
	}
	
	@GetMapping(value = "/getUserData/{username}")
	public ResponseEntity<List<ElementModel>> getByUsername(@PathVariable(required = true, value = "username") String username){
		List<ElementModel> result = repo.getByUsername(username);
		return new ResponseEntity<List<ElementModel>>(result,HttpStatus.OK);
	}
	
	@PostMapping(value = "/add")
	public ElementModel add(@RequestBody ElementModel entity){
		entity.setId(null);
		ElementModel data = repo.insert(entity);
		return data;
	}
}
