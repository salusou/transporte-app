import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IStateProvinces, StateProvinces } from '../state-provinces.model';

import { StateProvincesService } from './state-provinces.service';

describe('Service Tests', () => {
  describe('StateProvinces Service', () => {
    let service: StateProvincesService;
    let httpMock: HttpTestingController;
    let elemDefault: IStateProvinces;
    let expectedResult: IStateProvinces | IStateProvinces[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(StateProvincesService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        stateName: 'AAAAAAA',
        abbreviation: 'AAAAAAA',
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

      it('should create a StateProvinces', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new StateProvinces()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a StateProvinces', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            stateName: 'BBBBBB',
            abbreviation: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a StateProvinces', () => {
        const patchObject = Object.assign({}, new StateProvinces());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of StateProvinces', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            stateName: 'BBBBBB',
            abbreviation: 'BBBBBB',
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

      it('should delete a StateProvinces', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addStateProvincesToCollectionIfMissing', () => {
        it('should add a StateProvinces to an empty array', () => {
          const stateProvinces: IStateProvinces = { id: 123 };
          expectedResult = service.addStateProvincesToCollectionIfMissing([], stateProvinces);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(stateProvinces);
        });

        it('should not add a StateProvinces to an array that contains it', () => {
          const stateProvinces: IStateProvinces = { id: 123 };
          const stateProvincesCollection: IStateProvinces[] = [
            {
              ...stateProvinces,
            },
            { id: 456 },
          ];
          expectedResult = service.addStateProvincesToCollectionIfMissing(stateProvincesCollection, stateProvinces);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a StateProvinces to an array that doesn't contain it", () => {
          const stateProvinces: IStateProvinces = { id: 123 };
          const stateProvincesCollection: IStateProvinces[] = [{ id: 456 }];
          expectedResult = service.addStateProvincesToCollectionIfMissing(stateProvincesCollection, stateProvinces);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(stateProvinces);
        });

        it('should add only unique StateProvinces to an array', () => {
          const stateProvincesArray: IStateProvinces[] = [{ id: 123 }, { id: 456 }, { id: 33185 }];
          const stateProvincesCollection: IStateProvinces[] = [{ id: 123 }];
          expectedResult = service.addStateProvincesToCollectionIfMissing(stateProvincesCollection, ...stateProvincesArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const stateProvinces: IStateProvinces = { id: 123 };
          const stateProvinces2: IStateProvinces = { id: 456 };
          expectedResult = service.addStateProvincesToCollectionIfMissing([], stateProvinces, stateProvinces2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(stateProvinces);
          expect(expectedResult).toContain(stateProvinces2);
        });

        it('should accept null and undefined values', () => {
          const stateProvinces: IStateProvinces = { id: 123 };
          expectedResult = service.addStateProvincesToCollectionIfMissing([], null, stateProvinces, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(stateProvinces);
        });

        it('should return initial array if no StateProvinces is added', () => {
          const stateProvincesCollection: IStateProvinces[] = [{ id: 123 }];
          expectedResult = service.addStateProvincesToCollectionIfMissing(stateProvincesCollection, undefined, null);
          expect(expectedResult).toEqual(stateProvincesCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
