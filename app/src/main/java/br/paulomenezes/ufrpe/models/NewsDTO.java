package br.paulomenezes.ufrpe.models;

import java.util.List;

@org.parceler.Parcel
public class NewsDTO
{
    private List<NewsContentDTO> content;

    private String numberOfElements;

    private String sort;

    private String last;

    private String totalElements;

    private String number;

    private String first;

    private String totalPages;

    private String size;

    public List<NewsContentDTO> getContent ()
    {
        return content;
    }

    public void setContent (List<NewsContentDTO> content)
    {
        this.content = content;
    }

    public String getNumberOfElements ()
    {
        return numberOfElements;
    }

    public void setNumberOfElements (String numberOfElements)
    {
        this.numberOfElements = numberOfElements;
    }

    public String getSort ()
{
    return sort;
}

    public void setSort (String sort)
    {
        this.sort = sort;
    }

    public String getLast ()
    {
        return last;
    }

    public void setLast (String last)
    {
        this.last = last;
    }

    public String getTotalElements ()
    {
        return totalElements;
    }

    public void setTotalElements (String totalElements)
    {
        this.totalElements = totalElements;
    }

    public String getNumber ()
    {
        return number;
    }

    public void setNumber (String number)
    {
        this.number = number;
    }

    public String getFirst ()
    {
        return first;
    }

    public void setFirst (String first)
    {
        this.first = first;
    }

    public String getTotalPages ()
    {
        return totalPages;
    }

    public void setTotalPages (String totalPages)
    {
        this.totalPages = totalPages;
    }

    public String getSize ()
    {
        return size;
    }

    public void setSize (String size)
    {
        this.size = size;
    }
}
