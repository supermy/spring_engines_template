/**
 * Created by moyong on 14/12/27.
 */
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

/**
 * <p>模板引擎Mustache处理<p/>
 * <p>提供list变量</p>
 */
public class MustacheTest  {

    List<Item> items() {
        return Arrays.asList(
                new Item("苹果", "$19.99", Arrays.asList(new Feature("新上市"), new Feature("红富士"))),
                new Item("樱桃", "$29.99", Arrays.asList(new Feature("4月上市"), new Feature("无子")))
        );
    }

    static class Item {
        Item(String name, String price, List<Feature> features) {
            this.name = name;
            this.price = price;
            this.features = features;
        }

        String name, price;
        List<Feature> features;
    }

    static class Feature {
        Feature(String description) {
            this.description = description;
        }

        String description;
    }

    public static void main(String[] args) throws IOException {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile("template.mustache");

        mustache.execute(new PrintWriter(System.out), new MustacheTest()).flush();
    }

}
