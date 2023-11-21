package org.binaracademy.bioskopbackend.service;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.binaracademy.bioskopbackend.model.response.OrderDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class InvoiceServiceImpl implements InvoiceService{

    @Override
    public byte[] generateInvoice(String username) throws FileNotFoundException, JRException {
        log.info("Generating invoice for {}", username);
        List<OrderDetail> orderDetails = Arrays.asList(
                OrderDetail.builder().productName("Nasi Goreng").price("Rp. 30.000").quantity(2L).build(),
                OrderDetail.builder().productName("Mie Goreng").price("Rp. 20.000").quantity(2L).build(),
                OrderDetail.builder().productName("Es Teh Manis").price("Rp. 20.000").quantity(4L).build()
        );

        Map<String, Object> parameterMap = new HashMap<>();
        parameterMap.put("username", username);
        parameterMap.put("finalPrice", "Rp. 70.000");
        parameterMap.put("orderDetail", orderDetails);
        JasperPrint invoice = JasperFillManager.fillReport(
                JasperCompileManager.compileReport(ResourceUtils.getFile("classpath:invoice_v2.jrxml").getAbsolutePath()),
                parameterMap,
                new JRBeanCollectionDataSource(orderDetails)
        );

        return JasperExportManager.exportReportToPdf(invoice);
    }
}
