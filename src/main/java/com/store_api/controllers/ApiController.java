package com.store_api.controllers;

import com.store_api.model.request.UpdateCountOrPriceOfGoodsRequest;
import com.store_api.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {
    private final GoodService goodService;

    @PutMapping("/shipment")
    public ResponseEntity<?> shipment(
            @RequestBody UpdateCountOrPriceOfGoodsRequest request
    ) {
        return goodService.shipment(request);
    }
}
