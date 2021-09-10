import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { InspectionPositions } from 'app/entities/enumerations/inspection-positions.model';
import { VehicleStatus } from 'app/entities/enumerations/vehicle-status.model';
import { IVehicleInspectionsImagens, VehicleInspectionsImagens } from '../vehicle-inspections-imagens.model';

import { VehicleInspectionsImagensService } from './vehicle-inspections-imagens.service';

describe('Service Tests', () => {
  describe('VehicleInspectionsImagens Service', () => {
    let service: VehicleInspectionsImagensService;
    let httpMock: HttpTestingController;
    let elemDefault: IVehicleInspectionsImagens;
    let expectedResult: IVehicleInspectionsImagens | IVehicleInspectionsImagens[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(VehicleInspectionsImagensService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        vehicleInspectionsImagensPositions: InspectionPositions.FRONT,
        vehicleInspectionsImagensStatus: VehicleStatus.DIRTY,
        vehicleInspectionsImagensObs: 'AAAAAAA',
        vehicleInspectionsImagensPhotoContentType: 'image/png',
        vehicleInspectionsImagensPhoto: 'AAAAAAA',
        vehicleInspectionsImagensPositionsX: 0,
        vehicleInspectionsImagensPositionsY: 0,
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

      it('should create a VehicleInspectionsImagens', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new VehicleInspectionsImagens()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a VehicleInspectionsImagens', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            vehicleInspectionsImagensPositions: 'BBBBBB',
            vehicleInspectionsImagensStatus: 'BBBBBB',
            vehicleInspectionsImagensObs: 'BBBBBB',
            vehicleInspectionsImagensPhoto: 'BBBBBB',
            vehicleInspectionsImagensPositionsX: 1,
            vehicleInspectionsImagensPositionsY: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a VehicleInspectionsImagens', () => {
        const patchObject = Object.assign(
          {
            vehicleInspectionsImagensStatus: 'BBBBBB',
            vehicleInspectionsImagensPhoto: 'BBBBBB',
          },
          new VehicleInspectionsImagens()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of VehicleInspectionsImagens', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            vehicleInspectionsImagensPositions: 'BBBBBB',
            vehicleInspectionsImagensStatus: 'BBBBBB',
            vehicleInspectionsImagensObs: 'BBBBBB',
            vehicleInspectionsImagensPhoto: 'BBBBBB',
            vehicleInspectionsImagensPositionsX: 1,
            vehicleInspectionsImagensPositionsY: 1,
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

      it('should delete a VehicleInspectionsImagens', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addVehicleInspectionsImagensToCollectionIfMissing', () => {
        it('should add a VehicleInspectionsImagens to an empty array', () => {
          const vehicleInspectionsImagens: IVehicleInspectionsImagens = { id: 123 };
          expectedResult = service.addVehicleInspectionsImagensToCollectionIfMissing([], vehicleInspectionsImagens);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(vehicleInspectionsImagens);
        });

        it('should not add a VehicleInspectionsImagens to an array that contains it', () => {
          const vehicleInspectionsImagens: IVehicleInspectionsImagens = { id: 123 };
          const vehicleInspectionsImagensCollection: IVehicleInspectionsImagens[] = [
            {
              ...vehicleInspectionsImagens,
            },
            { id: 456 },
          ];
          expectedResult = service.addVehicleInspectionsImagensToCollectionIfMissing(
            vehicleInspectionsImagensCollection,
            vehicleInspectionsImagens
          );
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a VehicleInspectionsImagens to an array that doesn't contain it", () => {
          const vehicleInspectionsImagens: IVehicleInspectionsImagens = { id: 123 };
          const vehicleInspectionsImagensCollection: IVehicleInspectionsImagens[] = [{ id: 456 }];
          expectedResult = service.addVehicleInspectionsImagensToCollectionIfMissing(
            vehicleInspectionsImagensCollection,
            vehicleInspectionsImagens
          );
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(vehicleInspectionsImagens);
        });

        it('should add only unique VehicleInspectionsImagens to an array', () => {
          const vehicleInspectionsImagensArray: IVehicleInspectionsImagens[] = [{ id: 123 }, { id: 456 }, { id: 74119 }];
          const vehicleInspectionsImagensCollection: IVehicleInspectionsImagens[] = [{ id: 123 }];
          expectedResult = service.addVehicleInspectionsImagensToCollectionIfMissing(
            vehicleInspectionsImagensCollection,
            ...vehicleInspectionsImagensArray
          );
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const vehicleInspectionsImagens: IVehicleInspectionsImagens = { id: 123 };
          const vehicleInspectionsImagens2: IVehicleInspectionsImagens = { id: 456 };
          expectedResult = service.addVehicleInspectionsImagensToCollectionIfMissing(
            [],
            vehicleInspectionsImagens,
            vehicleInspectionsImagens2
          );
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(vehicleInspectionsImagens);
          expect(expectedResult).toContain(vehicleInspectionsImagens2);
        });

        it('should accept null and undefined values', () => {
          const vehicleInspectionsImagens: IVehicleInspectionsImagens = { id: 123 };
          expectedResult = service.addVehicleInspectionsImagensToCollectionIfMissing([], null, vehicleInspectionsImagens, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(vehicleInspectionsImagens);
        });

        it('should return initial array if no VehicleInspectionsImagens is added', () => {
          const vehicleInspectionsImagensCollection: IVehicleInspectionsImagens[] = [{ id: 123 }];
          expectedResult = service.addVehicleInspectionsImagensToCollectionIfMissing(vehicleInspectionsImagensCollection, undefined, null);
          expect(expectedResult).toEqual(vehicleInspectionsImagensCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
