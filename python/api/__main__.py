import logging

import click as click
import requests
import json

from api.utils.exceptions import StatusCodeException
from api.utils.logger import get_logger


@click.command()
@click.option('-s', '--service', required=True, type=click.Choice(['url', 'file', 'ip'], case_sensitive=False),
              help='The service you want to query')
@click.option('-r', '--resource', required=True, type=str, help='The resource you want to query')
@click.option('-ck', '--client-key', 'client_key', required=True, type=str, help='The client-key for your requests')
@click.option('-v', '--verbose', default=False, is_flag=True, help='More logs and prints the full response')
def main(service, resource, client_key, verbose):
    if verbose:
        logger = get_logger(__name__, logging.DEBUG)
    else:
        logger = get_logger(__name__)
    logger.debug('first, let\'s get token fro rep-auth')
    token_res = requests.get('https://rep.checkpoint.com/rep-auth/service/v1.0/request',
                             headers={'Client-Key': client_key})
    if token_res.status_code != 200:
        raise StatusCodeException(token_res.status_code)
    logger.debug('success!')
    token = token_res.content
    logger.debug('now, let\'s query reputation')
    rep_res = requests.post(f'https://rep.checkpoint.com/{service}-rep/service/v2.0/query?resource={resource}',
                            json={
                                'request': [{'resource': resource}]
                            }, headers={'Client-Key': client_key, 'token': token})
    if rep_res.status_code != 200:
        raise StatusCodeException(rep_res.status_code)
    logger.debug('success!')
    logger.debug(f'your response is:\n{json.dumps(rep_res.json(), indent=2)}\n')
    response = rep_res.json()['response'][0]
    risk = response['risk']
    classification = response['reputation']['classification']
    logger.info(f'{resource} is {classification} with risk {risk}/100')


if __name__ == '__main__':
    try:
        main()
    except StatusCodeException as e:
        print(e)
