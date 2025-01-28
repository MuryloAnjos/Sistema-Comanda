package Model;

import static View.Caixa_GUI.pdds_table;
import static View.Caixa_GUI.vl_total;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.awt.Desktop;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

public class Caixa {

    boolean taxei = false;
    public String res = "";
    int op = 0;

    public static void totalizar() {

        double total = 0;

        for (int c = 0; c < pdds_table.getRowCount(); c++) {

            double preco = Double.parseDouble(String.format("%.2f", pdds_table.getValueAt(c, 3)));
            int qtd = Integer.parseInt(pdds_table.getValueAt(c, 2).toString());

            double mult = (preco * qtd);

            total = total + mult;

        }

        View.Caixa_GUI.vl_total.setText(String.format("%.2f", total));
    }

    public void Relatorio(comanda c) throws Exception {

        Document doc = null;
        OutputStream os = null;

        double total = 0;

        String pedidos = "";

        for (int i = 0; i < pdds_table.getRowCount(); i++) {

            String nome = (pdds_table.getValueAt(i, 1).toString());
            int qtd = Integer.parseInt(pdds_table.getValueAt(i, 2).toString());
            double preco = Double.parseDouble(pdds_table.getValueAt(i, 3).toString());

            double mult = (preco * qtd);

            total = total + mult;

            pedidos += qtd + "x - " + nome + " = " + mult + "\n";

        }

        try {

            //cria o documento tamanho A4, margens de 2,54cm
            doc = new Document(PageSize.A4, 72, 72, 72, 72);

            //cria a stream de saída
            os = new FileOutputStream("/C:/Users/Murylo/Documents/relatoriocomanda.pdf");

            //associa a stream de saída ao
            PdfWriter.getInstance(doc, os);

            //abre o documento
            doc.open();

//            HELVETICA: Uma fonte sans-serif muito comum e amplamente usada.
//            TIMES_ROMAN: Uma fonte serifada clássica.
//            COURIER: Uma fonte monoespaçada, adequada para código ou formatação de texto que exige largura fixa.
//            SMALL_ROMAN: Uma variação da fonte "Times Roman", mas com tamanho menor.
//            LATIN1: Uma fonte projetada para suportar os caracteres latinos.
            Font f = new Font(Font.FontFamily.HELVETICA, 15, Font.BOLD);

            Paragraph por = new Paragraph("VISTA RESTAURANTE", f);
            por.setSpacingAfter(20);
            doc.add(por);

            String x = JOptionPane.showInputDialog(null, "Aceita pagar taxa de serviço (10%) ?\n"
                    + "1 - Sim\n"
                    + "2 - Não");
            op = Integer.parseInt(x);

            double cmp = Double.parseDouble(vl_total.getText());

            if (op == 1) {
                c.setValor_total(Double.parseDouble(String.format("%.2f", (cmp + (cmp * 0.10)))));
                res = "Sim";
            } else {
                res = "Não";
            }

            int idcmd = c.getId();
            int num = c.getNum_mesa();
            double valor = c.getValor_total();

            Font f2 = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            Paragraph pf1 = new Paragraph("Id Comanda : " + idcmd + "\n"
                    + "Número da Mesa : " + num + "\n"
                    + "Pedidos: \n" + pedidos + "\n"
                    + "Taxa de Serviço (10% = " + String.format("%.2f" ,(cmp * 0.10)) + ") : " + res + "\n"
                    + "Valor Total : " + valor + "\n", f2);

            doc.add(pf1);

        } finally {

            if (doc != null) {

                //fechamento do documento
                doc.close();
            }

            if (os != null) {
                //fechamento da stream de saída
                os.close();
            }
        }

        Desktop.getDesktop().open(new File("/C:/Users/Murylo/Documents/relatoriocomanda.pdf"));
    }

}
