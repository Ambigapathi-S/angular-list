package com.example.backend.controller;

import com.example.backend.dto.ItemDto;
import com.example.backend.service.ItemService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("api/list")
public class ItemController {
    private ItemService itemService;
    private ModelMapper modelMapper;
    @PostMapping
    public ResponseEntity<ItemDto> saveListItem(@RequestBody ItemDto itemDto) {
        ItemDto savedItem = itemService.saveListItem(itemDto);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getAllItems() {
        List<ItemDto> itemList = itemService.getAllItems();
        return ResponseEntity.ok().body(itemList);
    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteListItem(@PathVariable Long id) {
        itemService.deleteListItem(id);
        return ResponseEntity.ok("Title deleted successfully!");
    }
}
