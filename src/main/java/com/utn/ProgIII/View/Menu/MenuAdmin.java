package com.utn.ProgIII.View.Menu;

import com.utn.ProgIII.View.ApiManager.ApiManagerImp;
import com.utn.ProgIII.dto.ProductDTO;
import com.utn.ProgIII.dto.ResponseProductSupplierDTO;
import com.utn.ProgIII.dto.UserWithCredentialDTO;
import com.utn.ProgIII.dto.ViewSupplierDTO;
import com.utn.ProgIII.mapper.ProductMapper;
import com.utn.ProgIII.mapper.ProductSupplierMapper;
import com.utn.ProgIII.mapper.SupplierMapper;
import com.utn.ProgIII.mapper.UserMapper;
import com.utn.ProgIII.model.Address.Address;
import com.utn.ProgIII.model.Credential.Credential;
import com.utn.ProgIII.model.Credential.Role;
import com.utn.ProgIII.model.Product.Product;
import com.utn.ProgIII.model.Product.ProductStatus;
import com.utn.ProgIII.model.ProductSupplier.ProductSupplier;
import com.utn.ProgIII.model.Supplier.Supplier;
import com.utn.ProgIII.model.User.User;
import com.utn.ProgIII.model.User.UserStatus;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MenuAdmin {

    //elegir opcion
    public static int chooseOption(Scanner scan){

        int opcion = 0;
        System.out.println("Ingrese una opcion:  ");
        opcion = scan.nextInt();
        scan.nextLine();
        return opcion;
    }

    public static void menuAdmin (Scanner scan, ApiManagerImp manager) throws IOException, InterruptedException {

        int opcion = 0;
        printMenuAdmin();
        opcion = chooseOption(scan);

        UserMapper userMapper = new UserMapper();
        User newUser = new User();
        User user;
        Credential newCredential = new Credential();
        UserWithCredentialDTO userDTO;
        UserWithCredentialDTO[] usersDTO;

        Map<String,String> queryParams =new HashMap<>();

        Product newProduct = new Product();
        Product product;
        ProductDTO productDTO;
        ProductMapper productMapper = new ProductMapper();
        ProductDTO[] productsDTO;

        Supplier newSupplier = new Supplier();
        Supplier supplier;
        Address newAddress = new Address();
        SupplierMapper supplierMapper = new SupplierMapper();
        ViewSupplierDTO supplierDTO;
        ViewSupplierDTO[] suppliersDTO;

        ProductSupplier newProductSupplier = new ProductSupplier();
        ProductSupplier productSupplier;
        ProductSupplierMapper productSupplierMapper = new ProductSupplierMapper();
        ResponseProductSupplierDTO productSupplierDTO;


        String id;
        String searchParam;
        BigDecimal value;

        switch (opcion){

            case 1:  //gestion usuario
                printSubMenuUser();
                opcion = chooseOption(scan);

                switch (opcion){
                    case 1:

                        System.out.println("-- Nuevo usuario --\n");

                        System.out.println("Nombre");
                        newUser.setFirstname(scan.nextLine());
                        System.out.println("Apellido");
                        newUser.setLastname(scan.nextLine());
                        System.out.println("DNI");
                        newUser.setDni(scan.nextLine());

                        System.out.println("Nombre usuario");
                        newCredential.setUsername(scan.nextLine());
                        System.out.println("Contraseña");
                        newCredential.setPassword(scan.nextLine());

                        printSubMenuRole ();
                        opcion = chooseOption(scan);

                        switch (opcion){
                            case 1:
                                newCredential.setRole(Role.ADMIN);
                                break;
                            case 2:
                                newCredential.setRole(Role.MANAGER);
                                break;
                            default:
                                newCredential.setRole(Role.EMPLOYEE);
                                break;

                        }

                        newUser.setCredential(newCredential);
                        newUser.setStatus(UserStatus.ENABLED);


                        userDTO = userMapper.toUserWithCredentialDTO(newUser);

                        userDTO = manager.Post("users", userDTO,  UserWithCredentialDTO.class);

                        System.out.println("-- Carga exitosa --");

                        //mostrar el listado actualizado  ?????????
                    break;

                    case 2:

                        System.out.println("-- Eliminar usuario --\n");

                        System.out.println("Ingrese el id del usuario");
                        id = scan.nextLine();

                        System.out.println("\nElija el tipo de eliminación que desea\n");
                        System.out.println("1. Eliminación logica");
                        System.out.println("2. Eliminación fisica\n");

                        opcion = chooseOption(scan);

                        if(opcion == 1){

                            System.out.println("-- Eliminación logica --\n");
                            queryParams =  Map.of("deletionType", "soft");
                        }
                        else {
                            System.out.println("-- Eliminación fisica --");
                            queryParams = Map.of("deletionType", "hard");

                        }
                        System.out.println("   Procesando...");
                        userDTO = manager.Delete("users", id, UserWithCredentialDTO.class, queryParams);
                        System.out.println("\n-- Eliminación exitosa --");

                        break;

                    case 3:

                        System.out.println("-- Modificación usuario --\n");

                        System.out.println("Ingrese el id del usuario");
                        id = scan.nextLine();

                        userDTO = manager.Get("users", id, UserWithCredentialDTO.class);

                        System.out.println(userDTO.toString());

                        user = userMapper.toEntity(userDTO);

                        while (opcion != 0) {

                            printSubMenuUpdateUser();
                            opcion = chooseOption(scan);

                            newCredential = user.getCredential();

                            switch (opcion) {
                                case 1:
                                    System.out.println("Nombre");
                                    user.setFirstname(scan.nextLine());
                                    break;
                                case 2:
                                    System.out.println("Apellido");
                                    user.setLastname(scan.nextLine());
                                    break;
                                case 3:
                                    System.out.println("DNI");
                                    user.setDni(scan.nextLine());
                                    break;
                                case 4:
                                    System.out.println("Usuario");
                                    newCredential.setUsername(scan.nextLine());
                                    break;
                                case 5:
                                    System.out.println("Contraseña");
                                    newCredential.setPassword(scan.nextLine());
                                    break;
                                case 6:

                                    printSubMenuRole();
                                    opcion = chooseOption(scan);

                                    switch (opcion) {
                                        case 1:
                                            newCredential.setRole(Role.ADMIN);
                                            break;
                                        case 2:
                                            newCredential.setRole(Role.MANAGER);
                                            break;
                                        default:
                                            newCredential.setRole(Role.EMPLOYEE);
                                            break;
                                    }
                                    break;

                                default:
                                    System.out.println("Opcion invalida");
                                    break;
                            }

                            System.out.println("¿Desea modificar otro campo?");
                            System.out.println("1. Si");
                            System.out.println("0. No");
                            opcion = scan.nextInt();
                            scan.nextLine();

                            user.setCredential(newCredential);
                            userDTO = manager.Put("users", user,id,  UserWithCredentialDTO.class);
                        }
                        break;

                    case 4:

                        System.out.println("-- Visualizar todos los usuario --\n");

                        usersDTO = manager.Get("users",null,UserWithCredentialDTO[].class);

                        for (UserWithCredentialDTO userWithCredentialDTO : usersDTO){
                            System.out.println(userWithCredentialDTO.toString());
                        }
                        break;

                    case 5:

                        printSubMenuFilter();
                        opcion = chooseOption(scan);

                        switch (opcion){
                            case 1:
                                System.out.println("Ingrese el Id del usuario");
                                id = scan.nextLine();

                                userDTO = manager.Get("users",id,UserWithCredentialDTO.class);

                                System.out.println(userDTO.toString());
                                break;

                            case 2:

                                printSubMenuRole();
                                opcion = chooseOption(scan);

                                switch (opcion){
                                    case 1:  //admin

                                        queryParams =  Map.of("role", "admin");
                                        System.out.println("   Procesando...");
                                        usersDTO = manager.Get("users", null, UserWithCredentialDTO[].class, queryParams);

                                        for (UserWithCredentialDTO userWithCredentialDTO : usersDTO){
                                            System.out.println(userWithCredentialDTO.toString());
                                        }

                                        break;

                                    case 2:  //manager

                                        queryParams =  Map.of("role", "manager");
                                        System.out.println("   Procesando...");
                                        usersDTO = manager.Get("users", null, UserWithCredentialDTO[].class, queryParams);

                                        for (UserWithCredentialDTO userWithCredentialDTO : usersDTO){
                                            System.out.println(userWithCredentialDTO.toString());
                                        }
                                        break;

                                    case 3:  //cajero

                                        queryParams =  Map.of("role", "employee");
                                        System.out.println("   Procesando...");
                                        usersDTO = manager.Get("users", null, UserWithCredentialDTO[].class, queryParams);

                                        for (UserWithCredentialDTO userWithCredentialDTO : usersDTO){
                                            System.out.println(userWithCredentialDTO.toString());
                                        }
                                        break;

                                    default:
                                        System.out.println("Opción invalida");
                                }
                                break;

                            case 3:

                                System.out.println("-- Estado del usuario --");

                                System.out.println("1. Activo");  //enabled
                                System.out.println("2. Inactivo");     //disabled

                                opcion = chooseOption(scan);

                                if(opcion == 1 ){

                                    queryParams =  Map.of("status", "enabled");
                                }else {

                                    queryParams =  Map.of("status", "disabled");

                                }
                                System.out.println("   Procesando...");
                                usersDTO = manager.Get("users", null, UserWithCredentialDTO[].class, queryParams);

                                for (UserWithCredentialDTO userWithCredentialDTO : usersDTO){
                                    System.out.println(userWithCredentialDTO.toString());
                                }

                                break;

                            case 0:
                                //volver atras
                                printSubMenuUser();

                            default:
                                System.out.println("Opción invalida");

                        }



                }
                break;

            case 2:  //gestion producto

                printSubMenuproduct();
                opcion = chooseOption(scan);

                switch (opcion){

                    case 1:
                        System.out.println("-- Nuevo producto --\n");

                        System.out.println("Nombre");
                        newProduct.setName(scan.nextLine());

                        newProduct.setStatus(ProductStatus.ENABLED);

                        productDTO = productMapper.toProductDTO(newProduct);
                        System.out.println("   Procesando...");
                        productDTO = manager.Post("product",productDTO,ProductDTO.class);

                        System.out.println("Carga exitosa");
                        //quiza deberia mostrar lista de productos, para demostrar que se actualizo. ??????
                        break;

                    case 2:

                        //Baja Logica de un producto
                        System.out.println("-- Eliminar un producto --\n");

                        System.out.println("Ingrese el id del producto");
                        id = scan.nextLine();

                        System.out.println("   Procesando...");

                        productDTO = manager.Delete("product",id,ProductDTO.class);

                        System.out.println("-- Eliminacion exitosa --");

                        break;

                    case 3:

                        System.out.println("-- Modificar un producto --\n");

                        System.out.println("Ingrese el id del producto");
                        id = scan.nextLine();

                        productDTO = manager.Get("product",id,ProductDTO.class);

                        System.out.println(productDTO.toString());

                        newProduct = productMapper.toEntity(productDTO);

                        while (opcion != 0) {

                            System.out.println("-- Ingrese el campo que desea modificar --\n");

                            System.out.println("1. Nombre");
                            System.out.println("2. Estado");
                            opcion = chooseOption(scan);

                            switch (opcion) {
                                case 1:
                                    System.out.println("Nombre");
                                    newProduct.setName(scan.nextLine());
                                    break;

                                case 2:
                                    System.out.println("Estado");

                                    if (newProduct.getStatus().equals(ProductStatus.ENABLED)){
                                        newProduct.setStatus(ProductStatus.DISABLED);
                                    }
                                    else {
                                        newProduct.setStatus(ProductStatus.ENABLED);
                                    }

                                    break;

                                default:
                                    System.out.println("Opción invalida");
                            }

                            System.out.println("¿Desea modificar otro campo?");
                            System.out.println("1. Si");
                            System.out.println("0. No");
                            opcion = scan.nextInt();
                            scan.nextLine();

                            productDTO = manager.Put("product", newProduct,id,  ProductDTO.class);

                            //mostrar producto modificado !!!!!!!!!!!!!!!!!!
                        }
                        break;

                    case 4:

                        System.out.println("-- Visualizar todos los productos --\n");

                        productsDTO = manager.Get("product",null,ProductDTO[].class);

                        for (ProductDTO productDto : productsDTO){
                            System.out.println(productDto.toString());
                        }

                        break;

                    case 5:

                        printSubMenuFilterProduct();
                        opcion = chooseOption(scan);

                        switch (opcion){

                            case 1:

                                System.out.println("Ingrese el Id del producto");
                                id = scan.nextLine();

                                productDTO = manager.Get("product",id,ProductDTO.class);

                                System.out.println(productDTO.toString());

                                break;

                            case 2:

                                System.out.println("Ingrese el nombre del producto");
                                searchParam = scan.nextLine();

                                productsDTO = manager.Get("product/search/name",searchParam,ProductDTO[].class);

                                for (ProductDTO productDto : productsDTO){

                                    System.out.println(productDto.toString());
                                }

                                break;

                            case 3:

                                System.out.println("-- Estado del producto --");

                                System.out.println("1. Activo");  //enabled
                                System.out.println("2. Inactivo");     //disabled

                                opcion = chooseOption(scan);

                                if(opcion == 1 ){
                                    searchParam = "ENABLED";
                                }else {
                                    searchParam = "DISABLED";
                                }
                                System.out.println("   Procesando...");
                                productsDTO = manager.Get("product/search/status", searchParam, ProductDTO[].class);

                                for (ProductDTO productDto : productsDTO){
                                    System.out.println(productDto.toString());
                                }

                                break;

                            case 0:
                                //volver atras
                                break;

                            default:
                                System.out.println("Opción invalida");

                        }

                        break;

                    case 0:
                        //volver atras
                        break;
                    default:
                        System.out.println("Opción invalida");
                }

            case 3:  //Gestion proveedor

                printSubMenuSupplier();
                opcion = chooseOption(scan);

                switch (opcion){

                    case 1: //alta

                        System.out.println("-- Nuevo proveedor --\n");

                        System.out.println("Nombre compañia");
                        newSupplier.setCompanyName(scan.nextLine());
                        System.out.println("Cuit");
                        newSupplier.setCuit(scan.nextLine());
                        System.out.println("Telefono");
                        newSupplier.setPhoneNumber(scan.nextLine());
                        System.out.println("Email");
                        newSupplier.setEmail(scan.nextLine());

                        System.out.println("Calle");
                        newAddress.setStreet(scan.nextLine());
                        System.out.println("Numero");
                        newAddress.setNumber(scan.nextLine());
                        System.out.println("Ciudad");
                        newAddress.setCity(scan.nextLine());

                        newSupplier.setAddress(newAddress);

                        supplierDTO = supplierMapper.toViewSupplierDTO(newSupplier);

                        supplierDTO = manager.Post("supplier",supplierDTO, ViewSupplierDTO.class);

                        System.out.println("-- Carga exitosa");

                        // mostrar lista actualizada   ??????
                        break;

                    case 2:  //baja

                        System.out.println("-- Eliminar proveedor --\n");

                        System.out.println("Ingrese el id del proveedor");
                        id = scan.nextLine();

                        System.out.println("   Procesando...");
                        supplierDTO = manager.Delete("supplier",id,ViewSupplierDTO.class);
                        System.out.println("\n-- Eliminación exitosa --");

                        break;

                    case 3:  //modificar

                        System.out.println("-- Modificación proveedor --\n");

                        System.out.println("Ingrese el id del proveedor");
                        id = scan.nextLine();

                        supplierDTO = manager.Get("supplier", id, ViewSupplierDTO.class);

                        System.out.println(supplierDTO.toString());
                        supplier = supplierMapper.toObjectFromViewSupplierDTO(supplierDTO);

                        while (opcion != 0) {

                            printSubMenuUpdateSupplier();
                            opcion = chooseOption(scan);

                            newAddress = supplier.getAddress();

                            switch (opcion) {
                                case 1:
                                    System.out.println("Nombre compañia");
                                    supplier.setCompanyName(scan.nextLine());
                                    break;
                                case 2:
                                    System.out.println("Cuit");
                                    supplier.setPhoneNumber(scan.nextLine());
                                    break;
                                case 3:
                                    System.out.println("Telefono");
                                    supplier.setPhoneNumber(scan.nextLine());
                                    break;
                                case 4:
                                    System.out.println("Email");
                                    supplier.setEmail(scan.nextLine());
                                    break;
                                case 5:
                                    System.out.println("Calle");
                                    newAddress.setStreet(scan.nextLine());
                                    break;
                                case 6:
                                    System.out.println("Numero");
                                    newAddress.setNumber(scan.nextLine());
                                    break;
                                case 7:
                                    System.out.println("Ciudad");
                                    newAddress.setCity(scan.nextLine());
                                    break;
                                default:
                                    System.out.println("Opcion invalida");

                            }

                            System.out.println("¿Desea modificar otro campo?");
                            System.out.println("1. Si");
                            System.out.println("0. No");
                            opcion = scan.nextInt();
                            scan.nextLine();

                            supplier.setAddress(newAddress);
                            supplierDTO = manager.Put("supplier", supplier,id,  ViewSupplierDTO.class);

                            //Deberia volver a mostrarlo para verificar la modificacion ???????
                        }

                        break;

                    case 4:  //visualiza todos los proveedores

                        System.out.println("-- Visualizar todos los proveedores --\n");

                        suppliersDTO = manager.Get("supplier",null, ViewSupplierDTO[].class);

                        for (ViewSupplierDTO supplierDto : suppliersDTO){
                            System.out.println(supplierDto.toString());
                        }

                        break;

                    case 5:  //filtra

                        System.out.println("Ingrese el Id del proveedor");
                        id = scan.nextLine();
                        System.out.println("   Procesando...");

                        supplierDTO = manager.Get("supplier",id,ViewSupplierDTO.class);


                        System.out.println(supplierDTO.toString());

                        break;

                    case 0:
                        //volver atras (menu admin)
                        break;

                    default:
                        System.out.println("opción invalida");
                }

            case 4:  //Gestion producto-proveedor

                printSubMenuProductSupplier();
                opcion = chooseOption(scan);

                switch (opcion){

                    case 1:  //crear relacion
                        System.out.println("Ingrese el Id del proveedor");
                        id = scan.nextLine();
                        System.out.println("   Procesando...");

                        supplierDTO = manager.Get("supplier",id,ViewSupplierDTO.class);
                        supplier = supplierMapper.toObjectFromViewSupplierDTO(supplierDTO);

                        newProductSupplier.setSupplier(supplier);

                        System.out.println("Ingrese el Id del producto");
                        id = scan.nextLine();
                        System.out.println("   Procesando...");

                        productDTO = manager.Get("product",id,ProductDTO.class);
                        product = productMapper.toEntity(productDTO);

                        newProductSupplier.setProduct(product);

                        System.out.println("Ingrese el costo");
                        newProductSupplier.setCost(scan.nextBigDecimal());
                        scan.nextLine();
                        System.out.println("Ingrese el margen de ganancia");
                        newProductSupplier.setProfitMargin(scan.nextBigDecimal());
                        scan.nextLine();
                        newProductSupplier.setPrice(newProductSupplier.getCost().add(newProductSupplier.getCost().multiply(newProductSupplier.getProfitMargin()).divide(BigDecimal.valueOf(100), RoundingMode.CEILING)));

                        productSupplierDTO = productSupplierMapper.fromEntityToDto(newProductSupplier);

                        productSupplierDTO = manager.Post("productSupplier", productSupplierDTO,  ResponseProductSupplierDTO.class);

                        System.out.println("-- Carga exitosa --");

                        //mostrar el listado actualizado  ?????????
                        break;

                    case 2:  // modificar costo o margen ganancia  (ver patch)

                        break;

                    case 3:  // filtrar por id o nombre compañia

                        break;

                    default:
                        System.out.println("Opción invalida");
                }




        }
    }

    public static void printMenuAdmin (){
        System.out.println("-- Menu Administrador --\n");
        System.out.println("1. Gestion usuario");
        System.out.println("2. Gestion producto");
        System.out.println("3. Gestion proveedor");
        System.out.println("4. Gestion producto-proveedor");
        System.out.println("5. Actualización listas precios");
        System.out.println("6. Moneda extranjera");

        System.out.println("0. Volver atras");
    }

    public static void printSubMenuUser (){
        System.out.println("-- Gestion de usuario --\n");

        System.out.println("1. Crear nuevo usuario");
        System.out.println("2. Eliminar un usuario");
        System.out.println("3. Modificar un usuario");
        System.out.println("4. Visuarlizar todos los usuario");
        System.out.println("5. Filtrar usuarios");

        System.out.println("0. Volver atras");
    }

    public static void printSubMenuUpdateUser(){
        System.out.println("-- Ingrese el campo que desea modificar --\n");

        System.out.println("1. Nombre");
        System.out.println("2. Apellido");
        System.out.println("3. DNI");
        System.out.println("4. Usuario");
        System.out.println("5. Contraseña");
        System.out.println("6. Rol");

    }

    public static void printSubMenuRole(){
        System.out.println("Indique el rol");

        System.out.println("1. Administrador");
        System.out.println("2. Manager");
        System.out.println("3. Cajero");
    }

    public static void printSubMenuFilter(){
        System.out.println("-- Filtrar usuarios --");

        System.out.println("1. Numero Id");
        System.out.println("2. Rol");
        System.out.println("3. Estado");

        System.out.println("0. Volver atras");
    }

    public static void printSubMenuproduct(){
        System.out.println("-- Gestion producto --\n");

        System.out.println("1. Crear nuevo producto");
        System.out.println("2. Eliminar un producto");
        System.out.println("3. Modificar un producto");
        System.out.println("4. Visualizar todos los productos");
        System.out.println("5. Filtrar productos");

        System.out.println("0. Volver atras");

    }

    public static void printSubMenuFilterProduct(){
        System.out.println("-- Filtrar productos --");

        System.out.println("1. Numero Id");
        System.out.println("2. Nombre");
        System.out.println("3. Estado");

        System.out.println("0. Volver atras");
    }

    public static void printSubMenuSupplier(){
        System.out.println("-- Gestion proveedor --\n");

        System.out.println("1. Crear nuevo proveedor");
        System.out.println("2. Eliminar un proveedor");
        System.out.println("3. Modificar un proveedor");
        System.out.println("4. Visualizar todos los proveedores");
        System.out.println("5. Filtrar proveedores por Id");

        System.out.println("0. Volver atras");
    }

    public static void printSubMenuUpdateSupplier(){
        System.out.println("-- Ingrese el campo que desea modificar --\n");

        System.out.println("1. Nombre Compañia");
        System.out.println("2. Cuit");
        System.out.println("3. Telefono");
        System.out.println("4. Email");
        System.out.println("5. Calle");
        System.out.println("6. Numero");
        System.out.println("7. Ciudad");

    }

    public static void printSubMenuProductSupplier(){
        System.out.println("-- Gestion producto por proveedor --\n");

        System.out.println("1. Crear nueva asignación producto-proveedor");
        System.out.println("2. Modificar datos de relación");
        System.out.println("3. Filtrar producto-proveedor");

        System.out.println("0. Volver atras");
    }

}
