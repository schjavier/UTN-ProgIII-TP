package com.utn.ProgIII.dto;

import lombok.Builder;

@Builder

public record ProductDTO (Long idProduct,

                          String name,

                          String status) {


}
