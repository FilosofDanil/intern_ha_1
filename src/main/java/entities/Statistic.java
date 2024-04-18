package entities;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class which describe statistic. Useful for writing it in .xml file format.
 */
@XmlRootElement
public class Statistic {
    private String value;
    private int count;
    
    public Statistic() {
    }

    public Statistic(String value, int count) {
        this.value = value;
        this.count = count;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
