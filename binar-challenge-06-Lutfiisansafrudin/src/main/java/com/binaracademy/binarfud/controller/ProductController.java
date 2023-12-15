package com.binaracademy.binarfud.controller;

import com.binaracademy.binarfud.dto.request.CreateProductRequest;
import com.binaracademy.binarfud.dto.request.UpdateProductReqeust;
import com.binaracademy.binarfud.dto.response.base.APIResponse;
import com.binaracademy.binarfud.dto.response.base.APIResultResponse;
import com.binaracademy.binarfud.dto.response.ProductResponse;
import com.binaracademy.binarfud.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/product", produces = "application/json")
@AllArgsConstructor
@Slf4j
public class ProductController {
    ProductService productService;
    @PostMapping("/")
    @Schema(name = "CreateProductRequest", description = "Create product request body")
    @Operation(summary = "Endpoint to handle create new product")
    public ResponseEntity<APIResultResponse<ProductResponse>> createNewProduct(@RequestBody @Valid CreateProductRequest request) {
        log.info("Create new product request: {}", request);
        ProductResponse productResponse = productService.addNewProduct(request);
        APIResultResponse<ProductResponse> responseDTO =  new APIResultResponse<>(
                HttpStatus.CREATED,
                "Product successfully created",
                productResponse
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{productName}")
    @Schema(name = "UpdateProductRequest", description = "Update product request body")
    @Operation(summary = "Endpoint to handle update product")
    public ResponseEntity<APIResponse> updateProduct(@PathVariable String productName, @RequestBody @Valid UpdateProductReqeust request) {
        productService.updateProduct(productName, request);
        APIResponse responseDTO =  new APIResponse(
                HttpStatus.OK,
                "Product successfully updated"
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{productName}")
    @Schema(name = "DeleteProductRequest", description = "Delete product request body")
    @Operation(summary = "Endpoint to handle delete product")
    public ResponseEntity<APIResponse> deleteProduct(@PathVariable String productName) {
        productService.deleteProduct(productName);
        APIResponse responseDTO =  new APIResponse(
                HttpStatus.OK,
                "Product successfully deleted"
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{productName}")
    @Schema(name = "GetProductRequest", description = "Get product request body")
    @Operation(summary = "Endpoint to handle get product detail")
    public ResponseEntity<APIResultResponse<ProductResponse>> getProductDetail(@PathVariable String productName) {
        ProductResponse productResponse = productService.getProductDetail(productName);
        APIResultResponse<ProductResponse> responseDTO =  new APIResultResponse<>(
                HttpStatus.OK,
                "Product successfully retrieved",
                productResponse
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping
    @Schema(name = "GetProductRequest", description = "Get product request body")
    @Operation(summary = "Endpoint to handle get all product")
    public ResponseEntity<APIResultResponse<Page<ProductResponse>>> getProductsWithPagination(@RequestParam("page") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<ProductResponse> productResponses = productService.getProductsWithPagination(pageable);
        APIResultResponse<Page<ProductResponse>> responseDTO =  new APIResultResponse<>(
                HttpStatus.OK,
                "Products successfully retrieved",
                productResponses
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
