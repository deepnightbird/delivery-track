package  com.colvir.delivery;

import com.colvir.delivery.dto.PackageDto;
import com.colvir.delivery.dto.PackageStatusDto;
import com.colvir.delivery.message.TrackingEventMessage;
import com.colvir.delivery.model.Customer;
import com.colvir.delivery.model.TrackingEvent;
import com.colvir.delivery.model.TrackingStatus;
import com.colvir.delivery.repository.PackageRepository;
import com.colvir.delivery.repository.TrackingEventRepository;
import com.colvir.delivery.service.PackageTrackingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;

import static javax.management.Query.eq;
import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
class PackageServiceTest {
    @Mock
    private PackageRepository packageRepository;
    
    @Mock
    private TrackingEventRepository trackingEventRepository;
    
    @Mock
    private KafkaTemplate<String, TrackingEventMessage> kafkaTemplate;
    
    @InjectMocks
    private PackageTrackingService packageService;
    
    @Test
    void whenCreatePackage_thenReturnPackageDto() {
        // given
        PackageDto dto = new PackageDto("desc", 1.5, 1L, 2L);
        Customer sender = new Customer(1L, "Sender");
        Customer recipient = new Customer(2L, "Recipient");
        
        when(packageRepository.save(any(Package.class)))
            .thenAnswer(inv -> inv.getArgument(0));
        
        // when
        PackageDto result = packageService.createPackage(dto);
        
        // then
        assertNotNull(result);
        assertNotNull(result.getTrackingNumber());
        verify(packageRepository, times(1)).save(any(Package.class));
    }
    
    @Test
    void whenUpdateStatus_thenEventSavedAndKafkaMessageSent() {
        // given
        String trackingNumber = "TRACK123";
        Package pkg = new Package();
        pkg.setTrackingNumber(trackingNumber);
        
        when(packageRepository.findByTrackingNumber(trackingNumber))
            .thenReturn(Optional.of(pkg));
        
        PackageStatusDto dto = new PackageStatusDto(
            TrackingStatus.IN_TRANSIT, "Warehouse", "In transit");
        
        // when
        packageService.updateStatus(trackingNumber, dto);
        
        // then
        verify(trackingEventRepository, times(1)).save(any(TrackingEvent.class));
        verify(kafkaTemplate, times(1)).send(eq("tracking-events"), any(TrackingEventMessage.class));
    }
}
