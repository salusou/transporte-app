import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IServiceProvided, ServiceProvided } from '../service-provided.model';

import { ServiceProvidedService } from './service-provided.service';

describe('Service Tests', () => {
  describe('ServiceProvided Service', () => {
    let service: ServiceProvidedService;
    let httpMock: HttpTestingController;
    let elemDefault: IServiceProvided;
    let expectedResult: IServiceProvided | IServiceProvided[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ServiceProvidedService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        serviceName: 'AAAAAAA',
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ServiceProvided', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ServiceProvided()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ServiceProvided', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            serviceName: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a ServiceProvided', () => {
        const patchObject = Object.assign(
          {
            serviceName: 'BBBBBB',
          },
          new ServiceProvided()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ServiceProvided', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            serviceName: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ServiceProvided', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addServiceProvidedToCollectionIfMissing', () => {
        it('should add a ServiceProvided to an empty array', () => {
          const serviceProvided: IServiceProvided = { id: 123 };
          expectedResult = service.addServiceProvidedToCollectionIfMissing([], serviceProvided);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(serviceProvided);
        });

        it('should not add a ServiceProvided to an array that contains it', () => {
          const serviceProvided: IServiceProvided = { id: 123 };
          const serviceProvidedCollection: IServiceProvided[] = [
            {
              ...serviceProvided,
            },
            { id: 456 },
          ];
          expectedResult = service.addServiceProvidedToCollectionIfMissing(serviceProvidedCollection, serviceProvided);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a ServiceProvided to an array that doesn't contain it", () => {
          const serviceProvided: IServiceProvided = { id: 123 };
          const serviceProvidedCollection: IServiceProvided[] = [{ id: 456 }];
          expectedResult = service.addServiceProvidedToCollectionIfMissing(serviceProvidedCollection, serviceProvided);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(serviceProvided);
        });

        it('should add only unique ServiceProvided to an array', () => {
          const serviceProvidedArray: IServiceProvided[] = [{ id: 123 }, { id: 456 }, { id: 64672 }];
          const serviceProvidedCollection: IServiceProvided[] = [{ id: 123 }];
          expectedResult = service.addServiceProvidedToCollectionIfMissing(serviceProvidedCollection, ...serviceProvidedArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const serviceProvided: IServiceProvided = { id: 123 };
          const serviceProvided2: IServiceProvided = { id: 456 };
          expectedResult = service.addServiceProvidedToCollectionIfMissing([], serviceProvided, serviceProvided2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(serviceProvided);
          expect(expectedResult).toContain(serviceProvided2);
        });

        it('should accept null and undefined values', () => {
          const serviceProvided: IServiceProvided = { id: 123 };
          expectedResult = service.addServiceProvidedToCollectionIfMissing([], null, serviceProvided, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(serviceProvided);
        });

        it('should return initial array if no ServiceProvided is added', () => {
          const serviceProvidedCollection: IServiceProvided[] = [{ id: 123 }];
          expectedResult = service.addServiceProvidedToCollectionIfMissing(serviceProvidedCollection, undefined, null);
          expect(expectedResult).toEqual(serviceProvidedCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
