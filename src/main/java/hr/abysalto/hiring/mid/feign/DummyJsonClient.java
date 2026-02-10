package hr.abysalto.hiring.mid.feign;

import hr.abysalto.hiring.mid.service.product.dto.ProductDto;
import hr.abysalto.hiring.mid.service.product.dto.ProductSearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "dummyjson", url = "https://dummyjson.com")
public interface DummyJsonClient {

  @GetMapping("/products")
  ProductSearchDto getProducts(@RequestParam("limit") int limit,
                                     @RequestParam("skip") int skip);

  @GetMapping("/products/{id}")
  ProductDto getProductById(@PathVariable("id") Long id);
}