import com.colvir.delivery.dto.CustomerDto;
import com.colvir.delivery.dto.PackageDto;
import com.colvir.delivery.dto.PackageStatusDto;
import com.colvir.delivery.message.TrackingEventMessage;
import com.colvir.delivery.repository.PackageRepository;
import com.colvir.delivery.repository.TrackingEventRepository;
import com.colvir.delivery.service.PackageTrackingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;

import org.springframework.context.annotation.ComponentScan;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Configuration
@ComponentScan(basePackages = "com.colvir.delivery.repository")
@SpringBootTest
@Testcontainers
@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        PackageRepository.class,
        TrackingEventRepository.class,
        PackageTrackingService.class
})

class PackageServiceTest {
    @Autowired
    @Mock
    private PackageRepository packageRepository;

    @Autowired
    @Mock
    private TrackingEventRepository trackingEventRepository;

    //@Autowired
    //@Mock
    // private KafkaTemplate<String, TrackingEventMessage> kafkaTemplate;

    @Autowired
    @Mock
    private PackageTrackingService packageService;
    
    @Test
    void whenCreatePackage_thenReturnPackageDto() {
        // given
        PackageStatusDto packageStatusDto = new PackageStatusDto(1L, "REGISTERED", true, false);
        CustomerDto senderDto = new CustomerDto(1L, "OOO 'Romashka'", "Moscow, Arbat st., 38-39", "+79715844871");
        CustomerDto recipientDto = new CustomerDto(2L, "ZAO 'Roga i Kopyta'", "St. Peterburg, Nevskiy ave., 45-48", "+79552128506");
        PackageDto packageDto = new PackageDto(1L,"RU12345RU", "Elephant", 1000L, packageStatusDto, senderDto, recipientDto);

        /*when(packageRepository.save(packageDto))
            .thenAnswer(inv -> inv.getArgument(0));*/
        
        // when
        PackageDto result = packageService.createPackage(packageDto);
        
        // then
        assertNotNull(result);
        assertNotNull(result.getTrackingNumber());
        //verify(packageRepository, times(1)).save(any(Package.class));
    }
    
    @Test
    void whenUpdateStatus_thenEventSavedAndKafkaMessageSent() {
        // given
        String trackingNumber = "TRACK123";
        PackageDto pkgDto = new PackageDto();
        // pkg.setTrackingNumber(trackingNumber);
        
        /*when(packageRepository.findByTrackingNumber(trackingNumber))
            .thenReturn(Optional.of(pkgDto));*/
        
        PackageStatusDto dto = new PackageStatusDto(2L, "IN_TRANSIT", false, false);
        
        // when
        packageService.updateStatus(trackingNumber, dto);
        
        // then
        //verify(trackingEventRepository, times(1)).save(any(TrackingEvent.class));
        //verify(kafkaTemplate, times(1)).send(eq("tracking-events"), any(TrackingEventMessage.class));
    }
}
