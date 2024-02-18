import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.swing.JPanel;

class PrintablePanel extends JPanel implements Printable {
    private Book book;

    public PrintablePanel(Book book) {
        this.book = book;
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex >= book.getNumberOfPages()) {
            return Printable.NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        // 현재 페이지에 해당하는 내용을 Book에서 가져옴
        String pageContent = book.getPageContent(pageIndex);

        // 페이지 내용을 개행 문자(\n)를 기준으로 나누어 배열로 저장
        String[] lines = pageContent.split("\n");

        // 페이지의 폭과 높이
        double pageWidth = pf.getImageableWidth();
        double pageHeight = pf.getImageableHeight();

        // 글꼴 크기 설정
        g2d.setFont(new Font("굴림", Font.PLAIN, 12));

        // 각 줄을 출력
        double y = g2d.getFontMetrics().getHeight(); // 첫 줄 시작 위치
        for (String line : lines) {
            // 현재 줄을 출력할 때 필요한 폭과 높이
            double lineHeight = g2d.getFontMetrics().getHeight();

            // 현재 줄의 높이가 페이지의 높이를 벗어나면 페이지를 넘김
            if (y + lineHeight > pageHeight) {
                return Printable.PAGE_EXISTS;
            }

            // 현재 줄을 출력
            g2d.drawString(line, (float) pf.getImageableX(), (float) y);

            // 다음 줄의 시작 위치로 이동
            y += lineHeight;
        }

        // 페이지 내용이 모두 출력되었으므로 다음 페이지가 없음을 알림
        return Printable.PAGE_EXISTS;
    }
}


