package com.store_api.service;

import com.store_api.model.Category;
import com.store_api.model.Good;
import com.store_api.model.request.NewGoodRequest;
import com.store_api.model.request.UpdateCountOrPriceOfGoodsRequest;
import com.store_api.model.response.GoodResponse;
import com.store_api.repository.CategoryRepository;
import com.store_api.repository.GoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodService {
    private final GoodRepository goodRepository;
    private final CategoryRepository categoryRepository;

    private Good getGoodById(Long id) {
        return goodRepository.findById(id).orElse(null);
    }

    public ResponseEntity<?> getById(Long id) {
        Good good = getGoodById(id);
        return good != null
                ? new ResponseEntity<>(goodToGoodResponse(good), HttpStatus.OK)
                : new ResponseEntity<>("Товар не найден", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getAllGoods() {
        List<Good> goods = goodRepository.findAll();
        List<GoodResponse> goodResponses = new ArrayList<>();
        for (Good good : goods) {
            goodResponses.add(goodToGoodResponse(good));
        }
        return new ResponseEntity<>(goodResponses, HttpStatus.OK);
    }

    public ResponseEntity<?> getAllGoodsByCategoryId(Long id) {
        List<Good> goods = goodRepository.findAllByCategory_Id(id);
        List<GoodResponse> goodResponses = new ArrayList<>();
        for (Good good : goods) {
            goodResponses.add(goodToGoodResponse(good));
        }
        return new ResponseEntity<>(goodResponses, HttpStatus.OK);
    }

    public ResponseEntity<?> newGood(NewGoodRequest goodRequest) {
        Category category = categoryRepository.findById(goodRequest.getCategoryId()).orElse(null);
        if (category != null){
            Good good = Good.builder()
                    .name(goodRequest.getName())
                    .price(goodRequest.getPrice())
                    .count(goodRequest.getCount())
                    .category(category)
                    .build();
            return isSaveGood(good)
                    ? new ResponseEntity<>(goodToGoodResponse(good), HttpStatus.OK)
                    : new ResponseEntity<>("Ошибка сохранения товара", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Категория не найдена", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> updateCountOfGoods(UpdateCountOrPriceOfGoodsRequest request) {
        Good good = getGoodById(request.getId());
        if (good != null){
            good.setCount(request.getValue());
            return isSaveGood(good)
                    ? new ResponseEntity<>(goodToGoodResponse(good), HttpStatus.OK)
                    : new ResponseEntity<>("Ошибка сохранения товара", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Товар не найден", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> addCountOfGoods(UpdateCountOrPriceOfGoodsRequest request) {
        if (request.getValue() > 0){
            Good good = getGoodById(request.getId());
            if (good != null){
                int newCount = good.getCount() + request.getValue();
                good.setCount(newCount);
                return isSaveGood(good)
                        ? new ResponseEntity<>(goodToGoodResponse(good), HttpStatus.OK)
                        : new ResponseEntity<>("Ошибка сохранения товара", HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>("Товар не найден", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Значение не может быть 0 или отрицательным", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> updatePriceOfGoods(UpdateCountOrPriceOfGoodsRequest request) {
        Good good = getGoodById(request.getId());
        if (good != null){
            good.setPrice(request.getValue());
            return isSaveGood(good)
                    ? new ResponseEntity<>(goodToGoodResponse(good), HttpStatus.OK)
                    : new ResponseEntity<>("Ошибка сохранения товара", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Товар не найден", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteGood(Long id){
        Good good = getGoodById(id);
        if (good != null) {
            return isDeleteGood(good)
                    ? new ResponseEntity<>("Товар удален", HttpStatus.OK)
                    : new ResponseEntity<>("Ошибка удаления товара", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Товар не найден", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> shipment(UpdateCountOrPriceOfGoodsRequest request) {
        Good good = getGoodById(request.getId());
        if (good != null){
            if (request.getValue() <= good.getCount() && request.getValue() > 0){
                int newCount = good.getCount() - request.getValue();
                good.setCount(newCount);
                return isSaveGood(good)
                        ? new ResponseEntity<>(goodToGoodResponse(good), HttpStatus.OK)
                        : new ResponseEntity<>("Ошибка сохранения товара", HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>("Значение не может быть <= 0 или больше текущего количества товара", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Товар не найден", HttpStatus.NOT_FOUND);
        }
    }

    private boolean isSaveGood(Good good) {
        try {
            goodRepository.save(good);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isDeleteGood(Good good) {
        try {
            goodRepository.delete(good);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private GoodResponse goodToGoodResponse(Good good){
        return GoodResponse.builder()
                .id(good.getId())
                .name(good.getName())
                .count(good.getCount())
                .price(good.getPrice())
                .categoryName(good.getCategory().getName())
                .build();
    }
}
