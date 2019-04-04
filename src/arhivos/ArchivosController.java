/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arhivos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Set;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 * @author Sebastian Quintero
 */
public class ArchivosController {

    private HashMap<String, DefaultMutableTreeNode> dictionary;
    private DefaultTreeModel arbol;
    private DefaultMutableTreeNode nodoHijo;

    public ArchivosController() {
        this.nodoHijo = new DefaultMutableTreeNode("C:");
        this.arbol = new DefaultTreeModel(this.nodoHijo);
        this.dictionary = new HashMap<>();
        dictionary.put("C:", this.nodoHijo);
    }

    public HashMap<String, DefaultMutableTreeNode> getDictionary() {
        return dictionary;
    }

    public void setDictionary(HashMap<String, DefaultMutableTreeNode> dictionary) {
        this.dictionary = dictionary;
    }

    public DefaultTreeModel getArbol() {
        return arbol;
    }

    public void setArbol(DefaultTreeModel arbol) {
        this.arbol = arbol;
    }

    public DefaultMutableTreeNode getNodoHijo() {
        return nodoHijo;
    }

    public void setNodoHijo(DefaultMutableTreeNode nodoHijo) {
        this.nodoHijo = nodoHijo;
    }

    public void insertarNodo(String padre, String nodoHijo) {
        //Valida que el padre exista
        if (dictionary.containsKey(padre)) {
            DefaultMutableTreeNode nodoPadre = dictionary.get(padre);
            if (!dictionary.containsKey(nodoHijo)) {
                this.nodoHijo = new DefaultMutableTreeNode(nodoHijo);
                dictionary.put(nodoHijo, this.nodoHijo);
                this.arbol.insertNodeInto(this.nodoHijo, nodoPadre, nodoPadre.getChildCount());
            }
        }
    }

    public void eliminarNodo(String nodo, DefaultMutableTreeNode eliminar) {

        this.nodoHijo = new DefaultMutableTreeNode(nodo);
        DefaultMutableTreeNode nodoEliminar = dictionary.get(nodo);
        dictionary.remove(nodo, nodoEliminar);
//        this.arbol.removeNodeFromParent(eliminar);
        this.arbol.removeNodeFromParent(eliminar);

    }

    public void modificar(String old, DefaultMutableTreeNode oldNod, String newNo) {
        DefaultMutableTreeNode newNode = oldNod;
        dictionary.remove(old);
        dictionary.put(newNo, newNode);
        oldNod.setUserObject(newNo);
        this.arbol.nodeChanged(oldNod);

    }
    
    public void mover(String archivo, String padre){
        DefaultMutableTreeNode archivoOld = dictionary.get(archivo);
        DefaultMutableTreeNode archivoPadre = dictionary.get(padre);
        dictionary.remove(archivo);
        arbol.removeNodeFromParent(archivoOld);
        dictionary.put(String.valueOf(archivoOld.getUserObject()) , archivoOld);
        arbol.insertNodeInto(archivoOld, archivoPadre, archivoPadre.getChildCount());
    }

    public void guardar() {
        String cadena = "";
        cadena = concatenar(dictionary.get("C:"), cadena, 0);
        System.out.println(cadena);

        try {
            JFileChooser mFile = new JFileChooser();
            mFile.showOpenDialog(null);
            //abrimos el archivo seleccionado
            File archGrahp = mFile.getSelectedFile();
            FileWriter write = new FileWriter(archGrahp, false);//false para sobrescribir el archivo
            write.write(cadena);
            write.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    public String concatenar(DefaultMutableTreeNode nodo, String cadena, int pos) {
        pos = 0;
        if (nodo.getChildCount() > 0) {
            int cantidadHijos = nodo.getChildCount();
            cadena += nodo.getUserObject() + ";";
            for (int i = 0; i < cantidadHijos; i++) {
                cadena += nodo.getChildAt(i).toString() + "#";
            }
            cadena += "-";
            while (cantidadHijos > 0) {

                cadena = concatenar((DefaultMutableTreeNode) nodo.getChildAt(pos), cadena, pos);
                cantidadHijos--;
                pos++;
            }
        }
//        else {
//            cadena += nodo.getUserObject() + "-";
//        }
        return cadena;
    }

    public DefaultTreeModel leerGraph() {
        // DefaultMutableTreeNode graph = new DefaultMutableTreeNode();
        try {
            JFileChooser mFile = new JFileChooser();
            mFile.showOpenDialog(null);
            //abrimos el archivo seleccionado
            File archGrahp = mFile.getSelectedFile();
            BufferedReader read = new BufferedReader(new FileReader(mFile.getSelectedFile()));
            String bufRead = read.readLine();

            if (bufRead != null) {
                llenarGraph(bufRead);
            }
            read.close();
        } catch (Exception err) {

        }
        return this.arbol;
    }

    private void llenarGraph(String dato) {

        String[] padre = dato.split("-");
        String[] list;
        if (padre != null) {
            for (int i = 0; i < padre.length; i++) {
                String[] hijos = padre[i].split(";");
                list = hijos[1].split("#");
                for (int j = 0; j < list.length; j++) {
                    insertarNodo(hijos[0], list[j]);
                }
            }
        }

    }

}
