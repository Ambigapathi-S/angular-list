package com.example.backend.service;

import com.example.backend.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto saveListItem(ItemDto listDto);

    List<ItemDto> getAllItems();
    void deleteListItem(Long id);
}
