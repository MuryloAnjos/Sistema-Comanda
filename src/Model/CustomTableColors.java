package Model;

import java.awt.Color;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomTableColors {
    public void customizeTable(javax.swing.JTable pedidos_table) {
        // Alterar a cor de fundo de todas as células
        pedidos_table.setBackground(new Color(37, 36, 36));

        // Alterar a cor do texto (branco)
        pedidos_table.setForeground(Color.WHITE);

        // Personalizar o cabeçalho
        JTableHeader header = pedidos_table.getTableHeader();
        header.setBackground(new Color(103, 103, 103));
        header.setForeground(Color.WHITE);

        // Garantir que as cores sejam mantidas ao perder foco/seleção
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column
            ) {
                // Configurar a aparência base
                java.awt.Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setBackground(new Color(37, 36, 36));
                component.setForeground(Color.WHITE);

                // Manter a seleção visível
                if (isSelected) {
                    component.setBackground(new Color(103, 103, 103));
                    component.setForeground(Color.WHITE);
                }

                return component;
            }
        };

        // Aplicar o renderizador às células
        for (int i = 0; i < pedidos_table.getColumnCount(); i++) {
            pedidos_table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
    }
}
