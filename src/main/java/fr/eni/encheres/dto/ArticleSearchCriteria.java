package fr.eni.encheres.dto;

import java.util.List;

public class ArticleSearchCriteria {
    private String searchText;
    private Long categoryId;
    private List<FilterType> searchFilter;

    public String getSearchText() {
        return searchText;
    }
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<FilterType> getSearchFilter() {
        return searchFilter;
    }
    public void setSearchFilter(List<FilterType> searchFilter) {
        this.searchFilter = searchFilter;
    }

    @Override
    public String toString() {
        return "ArticleSearchCriteria{" +
                "searchText='" + searchText + '\'' +
                ", categoryId=" + categoryId +
                ", filters=" + searchFilter +
                '}';
    }
}
