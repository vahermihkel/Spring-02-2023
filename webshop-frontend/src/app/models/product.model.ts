export class Product {
    constructor(
        public id: number,
        public name: string,
        public imgSrc: string,
        public price: number,
        public category: string,
        public description: string,
        public isActive: boolean
    ) {}
}