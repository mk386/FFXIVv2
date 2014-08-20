package se.ridorana.roze.stuff.FFXIVDatTools;

import java.io.IOException;

/**
 *
 */
public class FileBlockWrapper {
	IndexReader.FileBlock fileBlock;

	String indexFile;

	IndexReaderWrapper indexReaderWrapper;

	private DatSegment segment;

	public IndexReaderWrapper getIndexReaderWrapper() {
		return indexReaderWrapper;
	}

	public IndexReader.FileBlock getFileBlock() {
		return fileBlock;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FileBlockWrapper(");
		sb.append(fileBlock.toString());
		sb.append(", indexFile=");
		sb.append(indexFile);
		sb.append(", segment=");
		sb.append(segment.toString());
		sb.append(", indexReader=");
		sb.append(indexReaderWrapper.toString());
		sb.append(")");
		return super.toString();
	}

	public FileBlockWrapper(IndexReaderWrapper indexReaderWrapper, IndexReader.FileBlock fileBlock) throws IOException, DatSegment.HandlerException {
		this.fileBlock = fileBlock;
		this.indexFile = indexReaderWrapper.getReader().getSourceFile();
		this.indexReaderWrapper = indexReaderWrapper;
		this.segment = new DatSegment(indexFile, fileBlock);
		;
		if (!this.segment.isHeadersLoaded()) {
			segment.loadHeader(indexReaderWrapper.getReadChannel(fileBlock));
		}
		if (!this.segment.isDataLoaded()) {
			segment.loadData(indexReaderWrapper.getReadChannel(fileBlock));
		}
	}

	public DatSegment getSegment() {
		return segment;
	}

	public DatSegment getSegment(boolean preloadHeaders, boolean preloadData) throws IOException, DatSegment.HandlerException {
		if (preloadHeaders && !segment.isHeadersLoaded()) {
			segment.loadHeader(indexReaderWrapper.getReadChannel(fileBlock));
		}
		if (preloadData && !segment.isDataLoaded()) {
			segment.loadData(indexReaderWrapper.getReadChannel(fileBlock));
		}
		return segment;
	}

}
