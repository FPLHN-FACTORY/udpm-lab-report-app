//package com.labreportapp.portalprojects.infrastructure.exception.rest;
//
//import com.labreportapp.portalprojects.infrastructure.exception.PortalProjectsExceptionHandler;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
///**
// * @author thangncph26123
// */
//
//public abstract class PortalProjectsExceptionRestHandler<Z extends Exception>
//        extends PortalProjectsExceptionHandler<ResponseEntity<?>,Z> {
//
//    @Override
//    protected ResponseEntity<?> wrap(Z ex) {
//        return new ResponseEntity<>(wrapApi(ex), HttpStatus.BAD_REQUEST);
//    }
//
//    protected abstract Object wrapApi(Z ex);
//}
