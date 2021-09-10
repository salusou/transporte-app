import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICities, Cities } from '../cities.model';

import { CitiesService } from './cities.service';

describe('Service Tests', () => {
  describe('Cities Service', () => {
    let service: CitiesService;
    let httpMock: HttpTestingController;
    let elemDefault: ICities;
    let expectedResult: ICities | ICities[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CitiesService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        cityName: 'AAAAAAA',
        latitude: 0,
        longitude: 0,
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

      it('should create a Cities', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Cities()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Cities', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            cityName: 'BBBBBB',
            latitude: 1,
            longitude: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Cities', () => {
        const patchObject = Object.assign(
          {
            cityName: 'BBBBBB',
          },
          new Cities()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Cities', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            cityName: 'BBBBBB',
            latitude: 1,
            longitude: 1,
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

      it('should delete a Cities', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCitiesToCollectionIfMissing', () => {
        it('should add a Cities to an empty array', () => {
          const cities: ICities = { id: 123 };
          expectedResult = service.addCitiesToCollectionIfMissing([], cities);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(cities);
        });

        it('should not add a Cities to an array that contains it', () => {
          const cities: ICities = { id: 123 };
          const citiesCollection: ICities[] = [
            {
              ...cities,
            },
            { id: 456 },
          ];
          expectedResult = service.addCitiesToCollectionIfMissing(citiesCollection, cities);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Cities to an array that doesn't contain it", () => {
          const cities: ICities = { id: 123 };
          const citiesCollection: ICities[] = [{ id: 456 }];
          expectedResult = service.addCitiesToCollectionIfMissing(citiesCollection, cities);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(cities);
        });

        it('should add only unique Cities to an array', () => {
          const citiesArray: ICities[] = [{ id: 123 }, { id: 456 }, { id: 13066 }];
          const citiesCollection: ICities[] = [{ id: 123 }];
          expectedResult = service.addCitiesToCollectionIfMissing(citiesCollection, ...citiesArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const cities: ICities = { id: 123 };
          const cities2: ICities = { id: 456 };
          expectedResult = service.addCitiesToCollectionIfMissing([], cities, cities2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(cities);
          expect(expectedResult).toContain(cities2);
        });

        it('should accept null and undefined values', () => {
          const cities: ICities = { id: 123 };
          expectedResult = service.addCitiesToCollectionIfMissing([], null, cities, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(cities);
        });

        it('should return initial array if no Cities is added', () => {
          const citiesCollection: ICities[] = [{ id: 123 }];
          expectedResult = service.addCitiesToCollectionIfMissing(citiesCollection, undefined, null);
          expect(expectedResult).toEqual(citiesCollection);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
