package com.utn.ProgIII;

import com.utn.ProgIII.View.ApiManager.ApiManagerImp;
import com.utn.ProgIII.View.Menu.MenuAdmin;
import com.utn.ProgIII.dto.LoginRequestDTO;
import com.utn.ProgIII.dto.LoginResponseDTO;
import com.utn.ProgIII.dto.ProductDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;

import com.fasterxml.jackson.core.type.TypeReference;

@SpringBootApplication
public class ProgIiiApplication {

	public static void main(String[] args) {

		SpringApplication.run(ProgIiiApplication.class, args);

		ApiManagerImp manager = new ApiManagerImp();
		LoginRequestDTO cred = new LoginRequestDTO("cebollin","dferg532");

        try {
            LoginResponseDTO response = manager.Post("auth/login", cred,  LoginResponseDTO.class);

			ProductDTO[] productos = manager.Get("product", null,  ProductDTO[].class);

			for (ProductDTO product : productos){
				System.out.println(product.toString());
			}

			MenuAdmin.menuAdmin(new Scanner(System.in),manager);
			//   Convertir a List
			//    List<MiDTO> items = Arrays.asList(arrayResult);
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}





	}
}
