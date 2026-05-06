package com.user.managements.Database;

import com.user.managements.Model.Product;
import com.user.managements.Repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration // Bên trong chữa các bean methods sẽ được gọi mỗi khi ứng dụng chạy
// và bean methods này dùng để khởi tạo database hoặc 1 vài cái khác cho môi trường
public class Database {
    //logger
    // Dùng để in ra chi tiết hơn sout ví dụ in ra được cả thành công hay thất bại
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    @Bean // các phương thức sẽ chạy ngay khi mà App chạy
    CommandLineRunner initDatabase(ProductRepository productRepository){
        //CommandLineRunner là một cái interface cái dòng return nó tạo ra 1 đối tượng thực thi hàm run
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
//                Product productA = new Product("Iphone14","",200.0,2025,"");
//                Product productB = new Product("Iphone15","",200.0,2025,"");
//                logger.info("Inserting product "+productRepository.save(productA));
//                logger.info("Inserting product "+productRepository.save(productB));
                System.out.println("run");
            }
        };
    }
}
