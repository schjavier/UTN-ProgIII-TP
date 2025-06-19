package com.utn.ProgIII.service.implementations;

import com.utn.ProgIII.dto.ViewDolarDTO;
import com.utn.ProgIII.exceptions.UnexpectedServerErrorException;
import com.utn.ProgIII.mapper.DollarMapper;
import com.utn.ProgIII.service.interfaces.MiscService;
import org.json.JSONObject;
import org.juliganga.Exceptions.BadRequestException;
import org.juliganga.Exceptions.IncorrectParseMethodException;
import org.juliganga.Model.Request.BackendRequest;
import org.juliganga.Model.Request.JSONConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class MiscServiceImpl implements MiscService {
    private final String dolar_api_url = "https://dolarapi.com/v1/";

    @Autowired
    private DollarMapper dollarmapper;

    @Override
    public ViewDolarDTO searchDollarPrice() {
        JSONObject dolar;
        try {
            BackendRequest dollarrequest = new BackendRequest(dolar_api_url, "dolares/oficial");
            dolar = JSONConverter.makeJsonObject(dollarrequest.searchData());
        } catch (IOException e) {
            throw new UnexpectedServerErrorException("Ocurrio un error conectando a la API del precio del dolar");
        } catch (InterruptedException | IncorrectParseMethodException e) {
            throw new UnexpectedServerErrorException("Un error inesperado ocurrio en el servicio.");
        } catch (BadRequestException e) {
            throw new UnexpectedServerErrorException("Un error inesperado ocurrió en la API, puede no estar disponible", e.getHttpCode());
        }
        return dollarmapper.dollarJsonObjectToDTO(dolar);
    }

}
