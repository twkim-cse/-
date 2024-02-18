import java.util.ArrayList;

public class Book {
    private String content;
    private ArrayList<String> pages;

    public Book(String content) {
        this.content = content;
        this.pages = splitContentIntoPages(content);
    }

    // 전체 내용을 페이지 단위로 나누어 리스트로 저장
    private ArrayList<String> splitContentIntoPages(String content) {
        ArrayList<String> pages = new ArrayList<>();
        String[] lines = content.split("\n");
        StringBuilder currentPage = new StringBuilder();
        for (String line : lines) {
            // 현재 페이지에 추가
            currentPage.append(line).append("\n");
            // 현재 페이지가 한 페이지의 최대 줄 수에 도달하면 리스트에 추가하고 새 페이지 시작
            if (currentPage.toString().split("\n").length >= maxLinesPerPage()) {
                pages.add(currentPage.toString());
                currentPage = new StringBuilder();
            }
        }
        // 남은 부분 추가
        if (currentPage.length() > 0) {
            pages.add(currentPage.toString());
        }
        return pages;
    }

    // 전체 페이지 수 반환
    public int getNumberOfPages() {
        return pages.size();
    }

    // 특정 페이지의 내용 반환
    public String getPageContent(int pageIndex) {
        if (pageIndex >= 0 && pageIndex < pages.size()) {
            return pages.get(pageIndex);
        } else {
            return "";
        }
    }

    // 전체 내용 반환
    public String getContent() {
        return content;
    }

    // 한 페이지당 최대 줄 수 (임의로 지정)
    private int maxLinesPerPage() {
        return 30;
    }
}


