
package models;

import java.util.List;

public class PetPayload {
    public long id;
    public Category category;
    public String name;
    public List<String> photoUrls;
    public List<PetTag> tags;
    public String status;
}
