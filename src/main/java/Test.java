import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.util.FileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.InputStream;
import java.util.ArrayList;

public class Test {

    Model model;

    JButton byStarsButton;
    JButton byMarketButton;

    JComboBox mainCategoriesDropDown;
    JComboBox subCategoiresDropDown;

    JButton searchButton;

    JTextArea resultText;

    public static void main(String[] args) {
      // Test ex = new Test();
       Ice_Cream_UI icu = new Ice_Cream_UI();
       icu.setVisible(true);

  
        InputStream in = FileManager.get().open( "F:\\BurgerOntology\\src\\main\\java\\BurgerRDFNew.owl" );
        if (in == null) {
            throw new IllegalArgumentException(
                    "File: " + "OwlFile" + " not found");
        }
    }

    private void searchOntology(String mainCategory, String subProperty) {
        String queryString =
                "PREFIX uni: <http://www.hotels.com/ontologies/hotel.owl#>" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                "SELECT ?x WHERE {" +
                "   ?y rdf:type uni:"+ subProperty + " .";
        if("PriceRange".equals(mainCategory)) {
             queryString += "   ?x uni:hasPrice ?y .";
        } else if("Facility".equals(mainCategory)) {
            queryString += " ?x uni:hasFacility ?y .";
        } else if("Market".equals(mainCategory)) {
            queryString += " ?x uni:hasMarket ?y .";
        } else if("Environment".equals(mainCategory)) {
            queryString += " ?x uni:hasSurroundedWith ?y .";
        } else if("Size".equals(mainCategory)) {
        } else if("Location".equals(mainCategory)) {
        } else if("Service".equals(mainCategory)) {
            queryString += " ?x uni:hasService ?y .";
        } else if("LevelOfService".equals(mainCategory)) {
            queryString += " ?x uni:hasProvidedServiceLevel ?y .";
        } else if("StarSystem".equals(mainCategory)) {
            queryString += " ?x uni:hasStarSystemOf ?y .";
        }
        queryString += "}";

        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);

        resultText.setText("");
        try {
            ResultSet resultSet = qexec.execSelect();
            while(resultSet.hasNext()) {
                QuerySolution solution = resultSet.nextSolution();
                RDFNode name = solution.get("x");
                resultText.append(name.toString().split("#")[1]);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
