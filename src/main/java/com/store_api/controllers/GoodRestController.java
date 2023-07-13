package com.store_api.controllers;

import com.store_api.model.request.NewGoodRequest;
import com.store_api.model.request.UpdateCountOrPriceOfGoodsRequest;
import com.store_api.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goods")
@RequiredArgsConstructor
public class GoodRestController {

    private final GoodService goodService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllGoods() {
        return goodService.getAllGoods();
    }

    @GetMapping("/category_{id}")
    public ResponseEntity<?> getAllGoods(
            @PathVariable Long id
    ) {
        return goodService.getAllGoodsByCategoryId(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable Long id
    ) {
        return goodService.getById(id);
    }

    @PostMapping("/new")
    public ResponseEntity<?> newCategory(
            @RequestBody NewGoodRequest goodRequest
    ) {
        return goodService.newGood(goodRequest);
    }

    @PutMapping("/new_price")
    public ResponseEntity<?> updatePrice(
            @RequestBody UpdateCountOrPriceOfGoodsRequest request
    ) {
        return goodService.updatePriceOfGoods(request);
    }

    @PutMapping("/new_count")
    public ResponseEntity<?> updateCount(
            @RequestBody UpdateCountOrPriceOfGoodsRequest request
    ) {
        return goodService.updateCountOfGoods(request);
    }

    @PutMapping("/add_count")
    public ResponseEntity<?> addCount(
            @RequestBody UpdateCountOrPriceOfGoodsRequest request
    ) {
        return goodService.addCountOfGoods(request);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<String> deleteGood(
            @PathVariable Long id
    ) {
        return goodService.deleteGood(id);
    }
}
