package com.utn.ProgIII.model.Product;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(defaultValue = "ENABLED")
public enum ProductStatus {
    ENABLED,
    DISABLED
}
