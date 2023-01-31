package com.programacion.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.programacion.dto.Author;
import com.programacion.dto.Books;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class AuthorApi {
    public static final String URL= "http://localhost:8082/authors";
    public Author findById(String id){

        try {
            HttpClient cliente= HttpClient.newHttpClient();
            HttpRequest solicitud = HttpRequest.newBuilder()
                    .uri(URI.create(URL+"/"+id))
                    .build();

            HttpResponse<String> respuesta =
                    cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());

            Author authors = new Gson().fromJson(respuesta.body(), Author.class);
            return authors ;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Author> findAll(){

        try {
            HttpClient cliente= HttpClient.newHttpClient();
            HttpRequest solicitud = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .build();
            HttpResponse<String> respuesta =
                    cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
            List<Author> authors = new Gson().fromJson(respuesta.body(), new TypeToken<List<Author>>() {}.getType());
            // System.out.println(books);
            return authors;
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
    public void update(String id,Author obj){
        try {
            //Permite que ASP.NET lea los valores HTTP enviados por un cliente durante una solicitud web.
            HttpRequest request = HttpRequest.newBuilder(URI.create(URL+"/"+id))
                    .header("Content-Type","application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(new Gson().toJson(obj)))
                    .build();
            HttpClient.newHttpClient()
                    .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::statusCode)
                    .thenAccept(System.out::println);
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void create( Author obj) {
        try {

            HttpRequest request = HttpRequest.newBuilder(URI.create(URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(new Gson().toJson(obj)))
                    .build();

            HttpClient.newHttpClient()
                    .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::statusCode)
                    .thenAccept(System.out::println);

        } catch (Exception e) {
            System.out.println(e);
        }

    }
    public void delete(String id){
        try {
            HttpClient cliente= HttpClient.newHttpClient();
            //Permite que ASP.NET lea los valores HTTP enviados por un cliente durante una solicitud web.
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL+"/"+id))
                    .DELETE().build();
            HttpResponse<String> response =
                    cliente.send(request, HttpResponse.BodyHandlers.ofString());
        }catch (Exception e){
            System.out.println(e);
        }
    }



}
