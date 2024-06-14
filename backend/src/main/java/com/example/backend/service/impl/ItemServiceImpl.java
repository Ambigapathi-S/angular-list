package com.example.backend.service.impl;

import com.example.backend.dto.ItemDto;
import com.example.backend.entity.Item;
import com.example.backend.exception.NameAlreadyExistsException;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.repository.ItemRepository;
import com.example.backend.service.ItemService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ItemServiceImpl implements ItemService {
    private ItemRepository listRepository;
    private ModelMapper modelMapper;
    @Override
    public ItemDto saveListItem(ItemDto listDto) {
        Item item = modelMapper.map(listDto, Item.class);
        // Check title is present or not
        Optional<Item> optionalItem = listRepository.findByTitle(item.getTitle());
        if(optionalItem.isPresent()) {
            throw new NameAlreadyExistsException("Title Already Exists in the list");
        }
        return modelMapper.map(listRepository.save(item), ItemDto.class);
    }

    @Override
    public List<ItemDto> getAllItems() {
        return listRepository.findAll()
                .stream().map(todo -> modelMapper.map(todo, ItemDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteListItem(Long id) {
        Item existingItem = listRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Title", "id", id));
        listRepository.deleteById(id);
    }
}
