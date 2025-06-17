package com.utn.ProgIII.mapper;

import com.utn.ProgIII.dto.ViewDolarDTO;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DollarMapper {

    public ViewDolarDTO dollarJsonObjectToDTO(JSONObject jsonObject)
    {
        String moneda = jsonObject.getString("moneda");
        String nombre = jsonObject.getString("nombre");
        String casa = jsonObject.getString("casa");
        BigDecimal venta = jsonObject.getBigDecimal("venta");
        BigDecimal compra = jsonObject.getBigDecimal("compra");
        String ultima_actualizacion = jsonObject.getString("fechaActualizacion");

        return new ViewDolarDTO(moneda,nombre,casa,venta,compra,ultima_actualizacion);
    }
}
