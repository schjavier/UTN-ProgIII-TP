package com.utn.ProgIII.service.interfaces;

import com.utn.ProgIII.dto.ViewDolarDTO;
import org.json.JSONObject;

import java.math.BigDecimal;

public interface MiscService {
    ViewDolarDTO searchDollarPrice();
}
