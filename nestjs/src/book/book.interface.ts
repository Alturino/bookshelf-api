export type BookData = {
  readonly author: string;
  readonly created_at: Date;
  readonly currentPage: number;
  readonly deleted_at: Date | null;
  readonly finished: boolean;
  readonly id: string;
  readonly name: string;
  readonly publisher: string;
  readonly reading: boolean;
  readonly summary: string;
  readonly totalPage: number;
  readonly year: Date;
};
