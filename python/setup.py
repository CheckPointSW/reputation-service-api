import setuptools

with open("README.md", "r") as fh:
    long_description = fh.read()

setuptools.setup(
    name="reputation-service-API-client",
    version="1.0.0",
    author="Check Point Software Technologies LTD.",
    author_email="aviadl@checkpoint.com",
    description="Reputation Service python example",
    long_description=long_description,
    long_description_content_type="text/markdown",
    url="http://www.checkpoint.com/",
    packages=setuptools.find_packages(),
    install_requires=[
        'click',
        'requests'
    ],
    entry_points={
        'console_scripts': ['reputation-api=api.__main__:main'],
    }
)
