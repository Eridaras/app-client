package com.programacion.api;

import com.google.gson.Gson;
import com.programacion.dto.Author;
import com.programacion.dto.Books;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.List;
import com.google.gson.reflect.TypeToken;

public class BooksApi {
    public static final String URL="http://localhost:7001/books";
    public List<Books> findAll(){

        try {
            System.out.println("entramos al Api");
            HttpClient cliente= HttpClient.newHttpClient();
            HttpRequest solicitud = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .build();
            HttpResponse<String> respuesta =
                    cliente.send(solicitud, BodyHandlers.ofString());

            List<Books> books = new Gson().fromJson(respuesta.body(), new TypeToken<List<Books>>() {}.getType());
           // System.out.println(books);
            return books;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
    public void update(String id,Books obj){
        try {
            //Permite que ASP.NET lea los valores HTTP enviados por un cliente durante una solicitud web.
            HttpRequest request = HttpRequest.newBuilder(URI.create(URL+"/"+id))
                    .header("Content-Type","application/json")
                    .PUT(BodyPublishers.ofString(new Gson().toJson(obj)))
                    .build();
            HttpClient.newHttpClient()
                    .sendAsync(request, BodyHandlers.ofString())
                    .thenApply(HttpResponse::statusCode)
                    .thenAccept(System.out::println);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public Books find(String id){
        try {
            //Proporciona una clase para enviar solicitudes HTTP y recibir respuestas HTTP de un recurso identificado por un URI.
            HttpClient api = HttpClient.newHttpClient();
            //Permite que ASP.NET lea los valores HTTP enviados por un cliente durante una solicitud web.
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL+"/"+id)).build();
            //Encapsula la información de respuesta HTTP de una operación ASP.NET.
            HttpResponse<String> response = api.send(request, BodyHandlers.ofString());
            return  new Gson().fromJson(response.body(),Books.class);
        }catch (Exception e){
            System.out.println(e);

        }
        return null;
    }
    public void delete(String id){
        try {
            System.out.println("Entramos al Delete");
            HttpClient cliente= HttpClient.newHttpClient();
            //Permite que ASP.NET lea los valores HTTP enviados por un cliente durante una solicitud web.
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL+"/"+id))
                    .DELETE().build();
            HttpResponse<String> response =
                    cliente.send(request, BodyHandlers.ofString());
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void create( Books obj) {
        try {

            HttpRequest request = HttpRequest.newBuilder(URI.create(URL))
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(new Gson().toJson(obj)))
                    .build();

            HttpClient.newHttpClient()
                    .sendAsync(request, BodyHandlers.ofString())
                    .thenApply(HttpResponse::statusCode)
                    .thenAccept(System.out::println);

        } catch (Exception e) {
            System.out.println(e);
        }

    }
    public List<Books> findByAuthor(String id){
        try {
            HttpClient cliente= HttpClient.newHttpClient();
            HttpRequest solicitud = HttpRequest.newBuilder()
                    .uri(URI.create(URL+"/author/"+id))
                    .build();

            HttpResponse<String> respuesta =
                    cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
            List<Books> authors = new Gson().fromJson(respuesta.body(), new TypeToken<List<Books>>() {}.getType());
            System.out.println(authors);
            return authors ;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

}
