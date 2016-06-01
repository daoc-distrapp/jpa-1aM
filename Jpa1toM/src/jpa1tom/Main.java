
package jpa1tom;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author dordonez@ute.edu.ec
 */
public class Main {

    private final EntityManager em = Persistence.createEntityManagerFactory("Jpa1toMPU").createEntityManager();
    
    //Uno(Autor) a Muchos(Libro) Unidireccional
    private void prueba() {
        Autor a;
        Libro l;
        //crea objetos
        a = creaAutor("diego");
        int id = a.getId();//guardamos la id para recuperarlo luego
        l = creaLibro("UNO");
        incluyeLibroEnAutor(a, l);
        l = creaLibro("DOS");
        incluyeLibroEnAutor(a, l);        
        //vaciamos variables en Java
        em.clear();
        a = null;
        l = null;
        //recupera Autor y revisa sus libros
        a = em.find(Autor.class, id);
        for(Libro lib : a.getLibros()) {
            System.out.println(lib.getTitulo());
        }        
    }
    
    private Autor creaAutor(String nombre) {
        Autor a = new Autor();
        a.setNombre(nombre);
        em.getTransaction().begin();
        em.persist(a);
        em.getTransaction().commit();
        return a;
    }
    
    private Libro creaLibro(String titulo) {
        Libro l = new Libro();
        l.setTitulo(titulo);
        em.getTransaction().begin();
        em.persist(l);
        em.getTransaction().commit();
        return l;
    }    
    
    private void incluyeLibroEnAutor(Autor a, Libro l) {
        em.getTransaction().begin();
        a.getLibros().add(l);
        em.getTransaction().commit();        
    }
    
    public static void main(String[] args) {
        Main main = new Main();
        main.prueba();
    }
    
}
