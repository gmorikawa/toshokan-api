package dev.gmorikawa.toshokan.document.whitepaper;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dev.gmorikawa.toshokan.document.DocumentFile;
import dev.gmorikawa.toshokan.document.DocumentFileService;
import dev.gmorikawa.toshokan.user.User;

@RestController
@RequestMapping(path = "api/whitepapers")
public class WhitepaperController {

    private final WhitepaperService service;
    private final DocumentFileService documentFileService;

    public WhitepaperController(WhitepaperService service, DocumentFileService documentFileService) {
        this.service = service;
        this.documentFileService = documentFileService;
    }

    @GetMapping()
    public List<Whitepaper> getAll() {
        return service.getAll();
    }

    @GetMapping("/year/{year}")
    public List<Whitepaper> getByYear(@PathVariable Integer year) {
        return service.getByYear(year);
    }

    @GetMapping("/{id}")
    public Whitepaper getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping()
    public Whitepaper create(
        @RequestAttribute("user") User requestor,
        @RequestBody Whitepaper entity
    ) {
        return service.create(requestor, entity);
    }

    @PostMapping("/{id}/upload")
    public DocumentFile upload(
        @RequestAttribute("user") User requestor,
        @PathVariable String id,
        @RequestParam("file") MultipartFile binary,
        @RequestParam("description") String description
    ) {
        Whitepaper whitepaper = service.getById(id);
        return documentFileService.create(requestor, whitepaper, binary, description);
    }

    @PatchMapping("/{id}")
    public Whitepaper update(
        @RequestAttribute("user") User requestor,
        @PathVariable String id,
        @RequestBody Whitepaper entity
    ) {
        return service.update(requestor, id, entity);
    }

    @DeleteMapping("/{id}")
    public boolean remove(
        @RequestAttribute("user") User requestor,
        @PathVariable String id
    ) {
        return service.remove(requestor, id);
    }
}
