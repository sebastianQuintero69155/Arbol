/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arhivos;

import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

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

}
