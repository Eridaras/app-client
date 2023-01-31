package com.programacion;

import com.programacion.api.AuthorApi;
import com.programacion.api.BooksApi;
import com.programacion.dto.Author;
import com.programacion.dto.Books;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import static spark.Spark.*;
public class Principal {
    public static void main(String[] args) {
        port(8081);
        BooksApi booksApi = new BooksApi();
        AuthorApi authoApi = new AuthorApi();
        get("/", (req, resp) -> {
            List<Books> books = booksApi.findAll();
            for (int i = 0; i < books.size(); i++) {
                Author autor =  authoApi.findById(books.get(i).getAuthor_id().toString());
                books.get(i).setAuthor(autor.getFirst_name() + " " +autor.getLast_name());
            }
            Map<String, Object> model = new HashMap<>();
            model.put("books",books );
            List<Author> authors = authoApi.findAll();
            model.put("authors",authors );

            //return model;
            return new ThymeleafTemplateEngine().render(new ModelAndView(model, "index"));

        });

        get("/books/:id",(request, response) -> {
            Books book = booksApi.find(request.params("id"));
            Map<String,Object> model = new HashMap<>();
            Author author =  authoApi.findById(book.getAuthor_id().toString());
            model.put("book",book);
            model.put("author",author );
            return new ThymeleafTemplateEngine().render(new ModelAndView(model,"update"));
        });
    //Update de Books y en el caso de necesitar update de Autor
        post("/books/update/:id", (req, resp) ->{
            String body = req.body();
            String[] parametros = body.split("&");
            String[] aux;
            Books p = new Books();
            Author author = new Author();
            for (int i = 0; i < parametros.length; i++) {
                parametros[i] = parametros[i].replace('+', ' ');
                aux = parametros[i].split("=");
                if(aux[0].equals("isbn")) {
                    p.setIsbn(aux[1]);
                }

                if(aux[0].equals("title")) {
                    p.setTitle(aux[1]);
                }
                if(aux[0].equals("price")) {
                    p.setPrice(Double.parseDouble(aux[1]));
                }
                if(aux[0].equals("author_id")) {
                    p.setAuthor_id(Integer.parseInt(aux[1]));
                }
                if(aux[0].equals("first_name")) {
                    author.setFirst_name(aux[1]);
                }
                if(aux[0].equals("last_name")) {
                    author.setLast_name(aux[1]);
                }
            }
            booksApi.update(req.params("id"), p);
           authoApi.update(p.getAuthor_id().toString(),author );

            List<Books> books = booksApi.findAll();
            for (int i = 0; i < books.size(); i++) {
                Author autor =  authoApi.findById(books.get(i).getAuthor_id().toString());
                books.get(i).setAuthor(autor.getFirst_name() + " " +autor.getLast_name());
            }
            Map<String, Object> model = new HashMap<>();
            model.put("books", books );
            List<Author> authors = authoApi.findAll();
            model.put("authors",authors );

            return new ThymeleafTemplateEngine().render(new ModelAndView(model, "index"));
        });

        get("/books/delete/:id", (req, resp) ->{
            booksApi.delete(req.params("id"));
            List<Books> books = booksApi.findAll();
            for (int i = 0; i < books.size(); i++) {
                Author autor =  authoApi.findById(books.get(i).getAuthor_id().toString());
                books.get(i).setAuthor(autor.getFirst_name() + " " +autor.getLast_name());
            }
            Map<String, Object> model = new HashMap<>();
            model.put("books", books );
            List<Author> authors = authoApi.findAll();
            model.put("authors",authors );
            return new ThymeleafTemplateEngine().render(new ModelAndView(model, "index"));
        });

        get("/create", (req, resp) ->{;
            Map<String, Object> model = new HashMap<>();
            return new ThymeleafTemplateEngine().render(new ModelAndView(model, "createdBooks"));
        });
        get("/author/create", (req, resp) ->{;
            Map<String, Object> model = new HashMap<>();
            return new ThymeleafTemplateEngine().render(new ModelAndView(model, "createdAutor"));
        });

        post("/", (req, resp) ->{
            String body = req.body();

            String[] parametros = body.split("&");
            String[] aux;
            Books p = new Books();
            for (int i = 0; i < parametros.length; i++) {
                parametros[i] = parametros[i].replace('+', ' ');
                aux = parametros[i].split("=");

                if(aux[0].equals("isbn")) {
                    p.setIsbn(aux[1]);
                }

                if(aux[0].equals("title")) {
                    p.setTitle(aux[1]);
                }
                if(aux[0].equals("price")) {
                    p.setPrice(Double.parseDouble(aux[1]));
                }
                if(aux[0].equals("author_id")) {
                    p.setAuthor_id(Integer.parseInt(aux[1]));
                }
            }
            booksApi.create(p);

            List<Books> books = booksApi.findAll();
            for (int i = 0; i < books.size(); i++) {
                Author autor =  authoApi.findById(books.get(i).getAuthor_id().toString());
                books.get(i).setAuthor(autor.getFirst_name() + " " +autor.getLast_name());
            }
            Map<String, Object> model = new HashMap<>();
            model.put("books", books );
            List<Author> authors = authoApi.findAll();
            model.put("authors",authors );
            return new ThymeleafTemplateEngine().render(new ModelAndView(model, "index"));
        });
        post("/author", (req, resp) ->{
            String body = req.body();

            String[] parametros = body.split("&");
            String[] aux;
            Author p = new Author();
            for (int i = 0; i < parametros.length; i++) {
                parametros[i] = parametros[i].replace('+', ' ');
                aux = parametros[i].split("=");

                if(aux[0].equals("first_name")) {
                    p.setFirst_name(aux[1]);
                }
                if(aux[0].equals("last_name")) {
                    p.setLast_name(aux[1]);
                }

            }
            authoApi.create(p);

            List<Books> books = booksApi.findAll();
            for (int i = 0; i < books.size(); i++) {
                Author autor =  authoApi.findById(books.get(i).getAuthor_id().toString());
                books.get(i).setAuthor(autor.getFirst_name() + " " +autor.getLast_name());
            }
            Map<String, Object> model = new HashMap<>();
            model.put("books", books );
            List<Author> authors = authoApi.findAll();
            model.put("authors",authors );
            return new ThymeleafTemplateEngine().render(new ModelAndView(model, "index"));
        });

        get("/author/delete/:id", (req, resp) ->{

            List<Books> booksdelete = booksApi.findByAuthor(req.params("id"));
            for (int i = 0; i < booksdelete.size(); i++) {
                booksApi.delete(booksdelete.get(i).getId().toString());
            }
            authoApi.delete(req.params("id"));

            List<Books> books = booksApi.findAll();
            for (int i = 0; i < books.size(); i++) {
                Author autor =  authoApi.findById(books.get(i).getAuthor_id().toString());
                books.get(i).setAuthor(autor.getFirst_name() + " " +autor.getLast_name());
            }
            Map<String, Object> model = new HashMap<>();
            model.put("books", books );
            List<Author> authors = authoApi.findAll();
            model.put("authors",authors );
            return new ThymeleafTemplateEngine().render(new ModelAndView(model, "index"));
        });
    }
}
