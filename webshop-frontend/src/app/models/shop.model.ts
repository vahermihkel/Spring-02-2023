export class Shop {
  constructor(
     public name: string,
     public address: string,
     public openTime: string,
     public closeTime: string,
     public latitude: number,
     public longitude: number,
     public id?: number
  ) {}
}