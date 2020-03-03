class StatusCodeException(Exception):
    def __init__(self, status_code: int, *args: object) -> None:
        super().__init__(*args)
        self.status_code = status_code

    def __str__(self) -> str:
        return "Operation failed with status {}".format(self.status_code)
